package com.mjc.school.view.console.command;

import lombok.Getter;

@Getter
public enum CommandDict {
    NEWS_GET_ALL(1, "Get all news."),
    NEWS_GET_BY_ID(2, "Get news by id."),
    NEWS_CREATE(3, "Create news."),
    NEWS_UPDATE(4, "Update news."),
    NEWS_REMOVE_BY_ID(5, "Remove news by id."),

    AUTHOR_GET_ALL(11, "Get all authors."),
    AUTHOR_GET_BY_ID(12, "Get an author by id."),
    AUTHOR_CREATE(13, "Create an author."),
    AUTHOR_UPDATE(14, "Update an author."),
    AUTHOR_REMOVE_BY_ID(15, "Remove an author by id."),
    ;

    private final long id;
    private final String description;

    CommandDict(long id, String description) {
        this.id = id;
        this.description = description;
    }
}