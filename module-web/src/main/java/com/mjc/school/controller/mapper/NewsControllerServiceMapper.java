package com.mjc.school.controller.mapper;

import com.mjc.school.controller.dto.NewsControllerRequestDto;
import com.mjc.school.controller.dto.NewsControllerResponseDto;
import com.mjc.school.service.dto.NewsServiceRequestDto;
import com.mjc.school.service.dto.NewsServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsControllerServiceMapper {

    NewsControllerResponseDto fromService(NewsServiceResponseDto dto);

    NewsServiceRequestDto toService(NewsControllerRequestDto dto);

    List<NewsControllerResponseDto> fromService(List<NewsServiceResponseDto> dto);
}
