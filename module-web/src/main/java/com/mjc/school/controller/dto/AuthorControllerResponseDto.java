package com.mjc.school.controller.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorControllerResponseDto {
    private Long id;
    private String name;
}