package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsServiceRequestDto;
import com.mjc.school.service.dto.NewsServiceResponseDto;
import com.mjc.school.service.error.ServiceErrorsDict;
import com.mjc.school.service.exception.UnifiedServiceException;
import com.mjc.school.service.mapper.NewsServiceRepositoryMapper;
import com.mjc.school.service.validation.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService implements BaseService<NewsServiceRequestDto, NewsServiceResponseDto, Long> {
    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final NewsServiceRepositoryMapper mapper;
    private final ValidatorService<NewsServiceRequestDto> validator;

    public NewsService(AuthorRepository authorRepository,
                       NewsRepository newsRepository,
                       NewsServiceRepositoryMapper mapper,
                       ValidatorService<NewsServiceRequestDto> validator) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<NewsServiceResponseDto> readAll() {
        return mapper.fromRepository(newsRepository.readAll());
    }

    @Override
    public NewsServiceResponseDto readById(Long id) {

        NewsModel newsModel = newsRepository.readById(id)
                .orElseThrow(() -> new UnifiedServiceException(ServiceErrorsDict.NEWS_WITH_ID_DOES_NOT_EXIST));

        return mapper.fromRepository(newsModel);
    }

    @Override
    public NewsServiceResponseDto create(NewsServiceRequestDto createRequest) {

        validator.validate(createRequest, ServiceErrorsDict.NEWS_DTO_VALIDATION);
        checkAuthorExist(createRequest);
        dtoIdMustBeNull(createRequest);

        NewsModel newsModel = mapper.toRepository(createRequest);
        NewsModel createdNewsModel = newsRepository.create(newsModel);

        return mapper.fromRepository(createdNewsModel);
    }

    @Override
    public NewsServiceResponseDto update(NewsServiceRequestDto updateRequest) {

        validator.validate(updateRequest, ServiceErrorsDict.NEWS_DTO_VALIDATION);
        checkAuthorExist(updateRequest);

        NewsModel newsModel = mapper.toRepository(updateRequest);
        NewsModel updatedNewsModel = newsRepository.update(newsModel);

        return mapper.fromRepository(updatedNewsModel);
    }

    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.readById(id).isEmpty()) {
            throw new UnifiedServiceException(ServiceErrorsDict.NEWS_WITH_ID_DOES_NOT_EXIST);
        }

        return newsRepository.deleteById(id);
    }

    private void checkAuthorExist(NewsServiceRequestDto object) {
        if (object == null || authorRepository.readById(object.getAuthorId()).isEmpty()) {
            throw new UnifiedServiceException(ServiceErrorsDict.NEWS_WITH_ID_DOES_NOT_EXIST);
        }
    }

    private void dtoIdMustBeNull(NewsServiceRequestDto object) {
        if (object == null || object.getId() != null) {
            throw new UnifiedServiceException(ServiceErrorsDict.NEWS_ID_MUST_BE_NULL_ON_CREATION);
        }
    }
}
