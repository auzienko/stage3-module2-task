package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import com.mjc.school.service.error.ServiceErrorsDict;
import com.mjc.school.service.exception.UnifiedServiceException;
import com.mjc.school.service.mapper.AuthorServiceRepositoryMapper;
import com.mjc.school.service.validation.ValidatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService implements BaseService<AuthorServiceRequestDto, AuthorServiceResponseDto, Long> {
    private final AuthorRepository authorRepository;
    private final AuthorServiceRepositoryMapper mapper;
    private final NewsRepository newsRepository;
    private final ValidatorService<AuthorServiceRequestDto> validator;

    public AuthorService(AuthorRepository authorRepository,
                         AuthorServiceRepositoryMapper mapper,
                         NewsRepository newsRepository,
                         ValidatorService<AuthorServiceRequestDto> validator) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.newsRepository = newsRepository;
        this.validator = validator;
    }

    @Override
    public List<AuthorServiceResponseDto> readAll() {
        return mapper.fromRepository(authorRepository.readAll());
    }

    @Override
    public AuthorServiceResponseDto readById(Long id) {

        AuthorModel authorModel = authorRepository.readById(id)
                .orElseThrow(() -> new UnifiedServiceException(ServiceErrorsDict.AUTHOR_WITH_ID_DOES_NOT_EXIST));

        return mapper.fromRepository(authorModel);
    }

    @Override
    public AuthorServiceResponseDto create(AuthorServiceRequestDto createRequest) {

        validator.validate(createRequest, ServiceErrorsDict.AUTHOR_DTO_VALIDATION);
        dtoIdMustBeNull(createRequest);

        AuthorModel authorModel = mapper.toRepository(createRequest);

        AuthorModel createdAuthorModel = authorRepository.create(authorModel);

        return mapper.fromRepository(createdAuthorModel);
    }

    @Override
    public AuthorServiceResponseDto update(AuthorServiceRequestDto updateRequest) {

        validator.validate(updateRequest, ServiceErrorsDict.AUTHOR_DTO_VALIDATION);

        AuthorModel authorModel = mapper.toRepository(updateRequest);
        AuthorModel updatedAuthorModel = authorRepository.update(authorModel);

        return mapper.fromRepository(updatedAuthorModel);
    }

    @Override
    public boolean deleteById(Long id) {
        if (authorRepository.readById(id).isEmpty()) {
            throw new UnifiedServiceException(ServiceErrorsDict.AUTHOR_WITH_ID_DOES_NOT_EXIST);
        }

        boolean result = authorRepository.deleteById(id);

        if (result) {
            removeNewsWhereAuthorId(id);
        }

        return result;
    }

    private void removeNewsWhereAuthorId(Long id) {
        List<NewsModel> newsModels = newsRepository.readAll();
        List<Long> newsForDelete = new ArrayList<>();
        newsModels.forEach(e -> {
            if (e.getAuthorId().equals(id)) {
                newsForDelete.add(e.getId());
            }
        });

        newsForDelete.forEach(newsRepository::deleteById);
    }

    private void dtoIdMustBeNull(AuthorServiceRequestDto object) {
        if (object == null || object.getId() != null) {
            throw new UnifiedServiceException(ServiceErrorsDict.AUTHOR_ID_MUST_BE_NULL_ON_CREATION);
        }
    }
}
