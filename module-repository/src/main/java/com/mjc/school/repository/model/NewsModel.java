package com.mjc.school.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NewsModel implements BaseEntity<Long> {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Long authorId;

    public NewsModel(NewsModel news) {
        setId(news.getId());
        setTitle(news.getTitle());
        setContent(news.getContent());
        setCreateDate(news.getCreateDate());
        setLastUpdateDate(news.getLastUpdateDate());
        setAuthorId(news.getAuthorId());
    }
}
