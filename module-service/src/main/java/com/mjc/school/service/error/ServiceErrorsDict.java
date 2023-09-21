package com.mjc.school.service.error;

import lombok.Getter;

@Getter
public enum ServiceErrorsDict {
    NEWS_WITH_ID_DOES_NOT_EXIST("000001", "News with id %d does not exist."),
    AUTHOR_WITH_ID_DOES_NOT_EXIST("000002", "Author Id does not exist. Author Id is: %s."),
    NEWS_ID_VALIDATION("000010", "News id can not be null or less than 1. News id is: %s."),
    AUTHOR_ID_VALIDATION("000010", "Author id can not be null or less than 1. Author id is: %s."),
    NEWS_DTO_VALIDATION("000012", "%s. News %s is %s."),
    NEWS_ID_MUST_BE_NULL_ON_CREATION("000014", "News id should be empty for new news."),
    AUTHOR_ID_MUST_BE_NULL_ON_CREATION("000014", "Author id should be empty for new author."),
    AUTHOR_DTO_VALIDATION("000015", "%s. Author %s is %s.."),
    ;

    private final String errorCode;
    private final String errorMessage;

    ServiceErrorsDict(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public void printLn(Object... args) {
        System.out.println("ERROR_CODE: " + errorCode + ", ERROR_MESSAGE: " + errorMessage.formatted(args));
    }
}
