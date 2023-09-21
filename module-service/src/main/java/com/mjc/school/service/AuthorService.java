package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import com.mjc.school.service.error.ServiceErrorsDict;
import com.mjc.school.service.exception.UnifiedServiceException;
import com.mjc.school.service.mapper.AuthorServiceRepositoryMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService implements BaseService<AuthorServiceRequestDto, AuthorServiceResponseDto, Long> {
    private final AuthorRepository authorRepository;

    private final AuthorServiceRepositoryMapper mapper;
    private final NewsRepository newsRepository;

    private final Validator validator;

    public AuthorService(AuthorRepository authorRepository, AuthorServiceRepositoryMapper mapper, NewsRepository newsRepository) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
        this.newsRepository = newsRepository;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
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

        validate(createRequest);
        dtoIdMustBeNull(createRequest);

        AuthorModel authorModel = mapper.toRepository(createRequest);

        AuthorModel createdAuthorModel = authorRepository.create(authorModel);

        return mapper.fromRepository(createdAuthorModel);
    }

    @Override
    public AuthorServiceResponseDto update(AuthorServiceRequestDto updateRequest) {

        validate(updateRequest);

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

    private void validate(AuthorServiceRequestDto dto) {
        Set<ConstraintViolation<AuthorServiceRequestDto>> violations = validator.validate(dto);
        Optional<ConstraintViolation<AuthorServiceRequestDto>> any = violations.stream().findAny();
        if (any.isPresent()) {
            String errorMessage = ServiceErrorsDict.AUTHOR_DTO_VALIDATION.getErrorMessage().formatted(
                    any.get().getPropertyPath().toString(),
                    any.get().getMessage(),
                    any.get().getInvalidValue().toString());

            throw new UnifiedServiceException(ServiceErrorsDict.AUTHOR_DTO_VALIDATION.getErrorCode(), errorMessage);
        }
    }

    private void dtoIdMustBeNull(AuthorServiceRequestDto object) {
        if (object == null || object.getId() != null) {
            throw new UnifiedServiceException(ServiceErrorsDict.AUTHOR_ID_MUST_BE_NULL_ON_CREATION);
        }
    }
}
