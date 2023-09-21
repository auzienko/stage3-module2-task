package com.mjc.school.controller.mapper;

import com.mjc.school.controller.dto.AuthorControllerRequestDto;
import com.mjc.school.controller.dto.AuthorControllerResponseDto;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorControllerServiceMapper {

    AuthorControllerResponseDto fromService(AuthorServiceResponseDto dto);

    AuthorServiceRequestDto toService(AuthorControllerRequestDto dto);

    List<AuthorControllerResponseDto> fromService(List<AuthorServiceResponseDto> dto);
}
