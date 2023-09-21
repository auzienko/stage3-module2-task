package com.mjc.school.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorServiceResponseDto {
    private Long id;
    private String name;
}
