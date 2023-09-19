package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.exception.NewsNotFoundException;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.dto.NewsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {

    private NewsService underTest;

    @BeforeEach
    void init() throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();

        String authorFileName = propertiesReader.getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        String newsFileName = propertiesReader.getProperties().getProperty(PropertiesName.NEWS_FILE);

        DataSource<AuthorModel> authorModelDataSource = new DataSource<>(authorFileName, AuthorModel.class);
        DataSource<NewsModel> newsModelDataSource = new DataSource<>(newsFileName, NewsModel.class);

        AuthorRepository authorRepository = new AuthorRepository(authorModelDataSource);
        NewsRepository newsRepository = new NewsRepository(newsModelDataSource);

        underTest = new NewsService(authorRepository, newsRepository);
    }

    @Test
    void findAll() {
        int expectedSize = 20;

        List<NewsModel> all = underTest.readAll();

        assertEquals(expectedSize, all.size());
    }

    @Test
    void findById() {
        NewsModel byId = underTest.readById(1L);

        assertEquals(1L, byId.getId());
    }

    @Test
    void create() {
        long expectedId = 21L;

        NewsDto requestDto = new NewsDto();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);

        NewsModel responseDto = underTest.create(requestDto);
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthorId(), responseDto.getAuthorId());
        assertEquals(expectedId, responseDto.getId());
        assertNotNull(responseDto.getLastUpdateDate());
        assertNotNull(responseDto.getCreateDate());
    }

    @Test
    void update() {
        long updateId = 1L;

        NewsDto requestDto = new NewsDto();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);
        requestDto.setId(updateId);

        NewsModel responseDto = underTest.update(requestDto);

        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthorId(), responseDto.getAuthorId());
        assertEquals(1L, responseDto.getId());
    }

    @Test
    void remove() {
        long entityId = 1L;

        NewsModel beforeTest = underTest.readById(entityId);

        underTest.deleteById(entityId);

        assertThrows(NewsNotFoundException.class,
                () -> {
                    underTest.readById(entityId);
                });
    }
}