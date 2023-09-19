package com.mjc.school.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorDto {
    private Long id;

    @Size(max = 15, min = 5, message = "Authors name can not be less than 5 and more than 15 symbols")
    private String name;
}
