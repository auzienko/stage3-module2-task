package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.dto.NewsControllerRequestDto;
import com.mjc.school.controller.dto.NewsControllerResponseDto;
import com.mjc.school.controller.exception.UnifiedControllerException;
import com.mjc.school.controller.mapper.NewsControllerServiceMapper;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.dto.NewsServiceRequestDto;
import com.mjc.school.service.dto.NewsServiceResponseDto;
import com.mjc.school.service.exception.UnifiedServiceException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsControllerRequestDto, NewsControllerResponseDto, Long> {
    private final NewsService service;
    private final NewsControllerServiceMapper mapper;

    public NewsController(NewsService service, NewsControllerServiceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @CommandHandler(value = "NEWS_GET_ALL")
    @Override
    public List<NewsControllerResponseDto> readAll() {
        try {
            return mapper.fromService(service.readAll());
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "NEWS_CREATE")
    @Override
    public NewsControllerResponseDto create(NewsControllerRequestDto createRequest) {
        try {
            NewsServiceRequestDto newsServiceRequestDto = mapper.toService(createRequest);
            NewsServiceResponseDto newsServiceResponseDto = service.create(newsServiceRequestDto);
            return mapper.fromService(newsServiceResponseDto);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "NEWS_GET_BY_ID")
    @Override
    public NewsControllerResponseDto readById(Long id) {
        try {
            return mapper.fromService(service.readById(id));
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "NEWS_UPDATE")
    @Override
    public NewsControllerResponseDto update(NewsControllerRequestDto updateRequest) {
        try {
            NewsServiceRequestDto newsServiceRequestDto = mapper.toService(updateRequest);
            NewsServiceResponseDto newsServiceResponseDto = service.update(newsServiceRequestDto);
            return mapper.fromService(newsServiceResponseDto);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "NEWS_REMOVE_BY_ID")
    @Override
    public boolean deleteById(Long id) {
        try {
            return service.deleteById(id);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }
}

