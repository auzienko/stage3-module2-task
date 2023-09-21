package com.mjc.school.controller.exception;

import com.mjc.school.service.exception.UnifiedServiceException;
import lombok.Getter;

@Getter
public class UnifiedControllerException extends RuntimeException {
    private final String errorCode;
    private final String errorMessage;

    public UnifiedControllerException(UnifiedServiceException e) {
        super();
        errorCode = e.getErrorCode();
        errorMessage = e.getErrorMessage();
    }

    public void printInfo() {
        System.out.println("ERROR_CODE: " + errorCode + ", ERROR_MESSAGE: " + errorMessage);
    }
}
