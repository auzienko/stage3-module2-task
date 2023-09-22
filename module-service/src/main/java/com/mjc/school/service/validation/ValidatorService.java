package com.mjc.school.service.validation;

import com.mjc.school.service.error.ServiceErrorsDict;
import com.mjc.school.service.exception.UnifiedServiceException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class ValidatorService<T> {
    private final Validator validator;

    public ValidatorService() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validatorFactory.close();
    }

    public void validate(T dto, ServiceErrorsDict error) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        Optional<ConstraintViolation<T>> any = violations.stream().findAny();
        if (any.isPresent()) {
            String errorMessage = error.getErrorMessage().formatted(
                    any.get().getPropertyPath().toString(),
                    any.get().getMessage(),
                    any.get().getInvalidValue().toString());

            throw new UnifiedServiceException(error.getErrorCode(), errorMessage);
        }
    }
}
