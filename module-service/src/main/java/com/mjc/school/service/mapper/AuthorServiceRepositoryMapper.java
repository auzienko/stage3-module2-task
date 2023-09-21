package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AuthorServiceRepositoryMapper {
    AuthorServiceResponseDto fromRepository(AuthorModel model);

    AuthorModel toRepository(AuthorServiceRequestDto dto);

    List<AuthorServiceResponseDto> fromRepository(List<AuthorModel> modelList);
}
