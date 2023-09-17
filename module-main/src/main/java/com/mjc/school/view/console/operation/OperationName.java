package com.mjc.school.view.console.operation;

import lombok.Getter;

@Getter
public enum OperationName {
    GET_ALL(1, "Get all news."),
    GET_BY_ID(2, "Get news by id."),
    CREATE(3, "Create news."),
    UPDATE(4, "Update news."),
    REMOVE_BY_ID(5, "Remove news by id."),
    ;

    private final long id;
    private final String description;

    OperationName(long id, String description) {
        this.id = id;
        this.description = description;
    }
}