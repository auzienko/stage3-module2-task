package com.mjc.school.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorModel implements BaseEntity<Long> {
    private Long id;
    private String name;

    public AuthorModel(AuthorModel author) {
        setId(author.getId());
        setName(author.getName());
    }
}
