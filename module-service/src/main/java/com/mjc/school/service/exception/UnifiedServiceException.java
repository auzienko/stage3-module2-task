package com.mjc.school.service.exception;

import com.mjc.school.service.error.ServiceErrorsDict;
import lombok.Getter;

@Getter
public class UnifiedServiceException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public UnifiedServiceException(ServiceErrorsDict error) {
        super();
        errorCode = error.getErrorCode();
        errorMessage = error.getErrorMessage();
    }

    public UnifiedServiceException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
