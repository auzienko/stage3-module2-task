package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsServiceRequestDto;
import com.mjc.school.service.dto.NewsServiceResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsServiceRepositoryMapper {
    NewsServiceResponseDto fromRepository(NewsModel model);

    NewsModel toRepository(NewsServiceRequestDto dto);

    List<NewsServiceResponseDto> fromRepository(List<NewsModel> modelList);
}
