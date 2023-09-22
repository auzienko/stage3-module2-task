package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.constant.PropertiesName;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.PropertiesReader;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import com.mjc.school.service.exception.UnifiedServiceException;
import com.mjc.school.service.mapper.AuthorServiceRepositoryMapper;
import com.mjc.school.service.validation.ValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthorServiceTest {

    private AuthorService underTest;

    private final AuthorServiceRepositoryMapper mapper = Mappers.getMapper(AuthorServiceRepositoryMapper.class);

    @BeforeEach
    void init() throws IOException {
        PropertiesReader propertiesReader = new PropertiesReader();

        String authorFileName = propertiesReader.getProperties().getProperty(PropertiesName.AUTHOR_FILE);
        String newsFileName = propertiesReader.getProperties().getProperty(PropertiesName.NEWS_FILE);

        DataSource<AuthorModel> authorModelDataSource = new DataSource<>(authorFileName, AuthorModel.class);
        DataSource<NewsModel> newsModelDataSource = new DataSource<>(newsFileName, NewsModel.class);

        AuthorRepository authorRepository = new AuthorRepository(authorModelDataSource);
        NewsRepository newsRepository = new NewsRepository(newsModelDataSource);

        ValidatorService<AuthorServiceRequestDto> validator = new ValidatorService<>();

        underTest = new AuthorService(authorRepository, mapper, newsRepository, validator);
    }

    @Test
    void findAll() {
        int expectedSize = 21;

        List<AuthorServiceResponseDto> all = underTest.readAll();

        assertEquals(expectedSize, all.size());
    }

    @Test
    void findById() {
        AuthorServiceResponseDto byId = underTest.readById(1L);

        assertEquals(1L, byId.getId());
    }

    @Test
    void create() {
        long expectedId = 22L;

        AuthorServiceRequestDto requestDto = new AuthorServiceRequestDto();
        requestDto.setName("my name");

        AuthorServiceResponseDto responseDto = underTest.create(requestDto);
        assertEquals(requestDto.getName(), responseDto.getName());
        assertEquals(expectedId, responseDto.getId());
    }

    @Test
    void update() {
        long updateId = 1L;

        AuthorServiceRequestDto requestDto = new AuthorServiceRequestDto();
        requestDto.setName("hello name");
        requestDto.setId(updateId);

        AuthorServiceResponseDto responseDto = underTest.update(requestDto);

        assertEquals(requestDto.getName(), responseDto.getName());
        assertEquals(1L, responseDto.getId());
    }

    @Test
    void remove() {
        long entityId = 1L;

        AuthorServiceResponseDto beforeTest = underTest.readById(entityId);

        underTest.deleteById(entityId);

        assertThrows(UnifiedServiceException.class,
                () -> {
                    underTest.readById(entityId);
                });
    }
}