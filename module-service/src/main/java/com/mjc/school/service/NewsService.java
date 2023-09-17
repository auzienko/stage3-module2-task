package com.mjc.school.service;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.exception.AuthorNotFoundException;
import com.mjc.school.repository.exception.NewsNotFoundException;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.NewsDtoValidationException;
import com.mjc.school.service.exception.NewsIdMustBeNullException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class NewsService implements BaseService<NewsDtoResponse, NewsModel, Long> {
    private final AuthorRepository authorRepository;
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    private final Validator validator;

    public NewsService(AuthorRepository authorRepository, NewsRepository newsRepository) {
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
    }

    @Override
    public List<NewsModel> readAll() {
        return newsRepository.readAll();
    }

    @Override
    public NewsModel readById(Long id) {
        return newsRepository.readById(id)
                .orElseThrow(NewsNotFoundException::new);
    }

    @Override
    public NewsModel create(NewsDtoResponse createRequest) {

        validate(createRequest);
        checkAuthorExist(createRequest);
        dtoIdMustBeNull(createRequest);

        NewsModel news = modelMapper.map(createRequest, NewsModel.class);

        return newsRepository.create(news);
    }

    @Override
    public NewsModel update(NewsDtoResponse updateRequest) {

        validate(updateRequest);
        checkAuthorExist(updateRequest);

        NewsModel news = modelMapper.map(updateRequest, NewsModel.class);

        return newsRepository.update(news);
    }

    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.readById(id).isEmpty()) {
            throw new NewsNotFoundException();
        }

        return newsRepository.deleteById(id);
    }

    private void validate(NewsDtoResponse dto) {
        Set<ConstraintViolation<NewsDtoResponse>> violations = validator.validate(dto);
        Optional<ConstraintViolation<NewsDtoResponse>> any = violations.stream().findAny();
        if (any.isPresent()) {
            throw new NewsDtoValidationException(
                    any.get().getPropertyPath().toString(),
                    any.get().getMessage(),
                    any.get().getInvalidValue().toString());
        }
    }

    private void checkAuthorExist(NewsDtoResponse object) {
        if (object == null || authorRepository.readById(object.getAuthorId()).isEmpty()) {
            throw new AuthorNotFoundException();
        }
    }

    private void dtoIdMustBeNull(NewsDtoResponse object) {
        if (object == null || object.getId() != null) {
            throw new NewsIdMustBeNullException();
        }
    }
}
