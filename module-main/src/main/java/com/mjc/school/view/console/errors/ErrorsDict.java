package com.mjc.school.view.console.errors;

import lombok.Getter;

@Getter
public enum ErrorsDict {
    OPERATION_NOT_FOUND("000000", "Command not found."),
    NEWS_ID_SHOULD_BE_NUMBER("000013", "News Id should be number."),
    AUTHOR_ID_SHOULD_BE_NUMBER("000013", "Author Id should be number."),
    ;

    private final String errorCode;
    private final String errorMessage;

    ErrorsDict(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void printLn(Object... args) {
        System.out.println("ERROR_CODE: " + errorCode + ", ERROR_MESSAGE: " + errorMessage.formatted(args));
    }
}
