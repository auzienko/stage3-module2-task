package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.exception.AuthorNotFoundException;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exception.AuthorDtoValidationException;
import com.mjc.school.service.exception.AuthorIdMustBeNullException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService implements BaseService<AuthorDto, AuthorModel, Long> {
    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private final Validator validator;

    public AuthorService(AuthorRepository authorRepository, NewsRepository newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
    }

    @Override
    public List<AuthorModel> readAll() {
        return authorRepository.readAll();
    }

    @Override
    public AuthorModel readById(Long id) {
        return authorRepository.readById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    @Override
    public AuthorModel create(AuthorDto createRequest) {

        validate(createRequest);
        dtoIdMustBeNull(createRequest);

        AuthorModel authorModel = modelMapper.map(createRequest, AuthorModel.class);

        return authorRepository.create(authorModel);
    }

    @Override
    public AuthorModel update(AuthorDto updateRequest) {

        validate(updateRequest);

        AuthorModel authorModel = modelMapper.map(updateRequest, AuthorModel.class);

        return authorRepository.update(authorModel);
    }

    @Override
    public boolean deleteById(Long id) {
        if (authorRepository.readById(id).isEmpty()) {
            throw new AuthorNotFoundException();
        }

        boolean result = authorRepository.deleteById(id);

        if (result) {
            removeNewsWhereAuthorId(id);
        }

        return result;
    }

    private void removeNewsWhereAuthorId(Long id) {
        List<NewsModel> newsModels = newsRepository.readAll();
        List<Long> newsForDelete =  new ArrayList<>();
        newsModels.forEach(e -> {
            if (e.getAuthorId().equals(id)) {
                newsForDelete.add(e.getId());
            }
        });

        newsForDelete.forEach(newsRepository::deleteById);
    }

    private void validate(AuthorDto dto) {
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(dto);
        Optional<ConstraintViolation<AuthorDto>> any = violations.stream().findAny();
        if (any.isPresent()) {
            throw new AuthorDtoValidationException(
                    any.get().getPropertyPath().toString(),
                    any.get().getMessage(),
                    any.get().getInvalidValue().toString());
        }
    }

    private void dtoIdMustBeNull(AuthorDto object) {
        if (object == null || object.getId() != null) {
            throw new AuthorIdMustBeNullException();
        }
    }
}
