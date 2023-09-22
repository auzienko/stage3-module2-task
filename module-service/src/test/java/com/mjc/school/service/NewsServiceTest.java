package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.dto.NewsServiceRequestDto;
import com.mjc.school.service.dto.NewsServiceResponseDto;
import com.mjc.school.service.exception.UnifiedServiceException;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.mapper.NewsServiceRepositoryMapper;
import com.mjc.school.service.validation.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {

    private NewsService underTest;
    private final NewsServiceRepositoryMapper mapper = Mappers.getMapper(NewsServiceRepositoryMapper.class);


    @BeforeEach
    void init() throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();

        String authorFileName = propertiesReader.getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        String newsFileName = propertiesReader.getProperties().getProperty(PropertiesName.NEWS_FILE);

        DataSource<AuthorModel> authorModelDataSource = new DataSource<>(authorFileName, AuthorModel.class);
        DataSource<NewsModel> newsModelDataSource = new DataSource<>(newsFileName, NewsModel.class);

        AuthorRepository authorRepository = new AuthorRepository(authorModelDataSource);
        NewsRepository newsRepository = new NewsRepository(newsModelDataSource);
        ValidatorService<NewsServiceRequestDto> validator = new ValidatorService<>();
        underTest = new NewsService(authorRepository, newsRepository, mapper, validator);
    }

    @Test
    void findAll() {
        int expectedSize = 20;

        List<NewsServiceResponseDto> all = underTest.readAll();

        assertEquals(expectedSize, all.size());
    }

    @Test
    void findById() {
        NewsServiceResponseDto byId = underTest.readById(1L);

        assertEquals(1L, byId.getId());
    }

    @Test
    void create() {
        long expectedId = 21L;

        NewsServiceRequestDto requestDto = new NewsServiceRequestDto();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);

        NewsServiceResponseDto responseDto = underTest.create(requestDto);
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

        NewsServiceRequestDto requestDto = new NewsServiceRequestDto();
        requestDto.setTitle("test title");
        requestDto.setContent("some content text");
        requestDto.setAuthorId(1L);
        requestDto.setId(updateId);

        NewsServiceResponseDto responseDto = underTest.update(requestDto);

        assertEquals(requestDto.getTitle(), responseDto.getTitle());
        assertEquals(requestDto.getContent(), responseDto.getContent());
        assertEquals(requestDto.getAuthorId(), responseDto.getAuthorId());
        assertEquals(1L, responseDto.getId());
    }

    @Test
    void remove() {
        long entityId = 1L;

        NewsServiceResponseDto beforeTest = underTest.readById(entityId);

        underTest.deleteById(entityId);

        assertThrows(UnifiedServiceException.class,
                () -> {
                    underTest.readById(entityId);
                });
    }
}