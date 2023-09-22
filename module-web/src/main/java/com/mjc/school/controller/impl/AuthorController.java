package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.dto.AuthorControllerRequestDto;
import com.mjc.school.controller.dto.AuthorControllerResponseDto;
import com.mjc.school.controller.exception.UnifiedControllerException;
import com.mjc.school.controller.mapper.AuthorControllerServiceMapper;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.dto.AuthorServiceRequestDto;
import com.mjc.school.service.dto.AuthorServiceResponseDto;
import com.mjc.school.service.exception.UnifiedServiceException;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorControllerRequestDto, AuthorControllerResponseDto, Long> {
    private final AuthorService service;
    private final AuthorControllerServiceMapper mapper;

    public AuthorController(AuthorService service, AuthorControllerServiceMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @CommandHandler(value = "AUTHOR_GET_ALL")
    @Override
    public List<AuthorControllerResponseDto> readAll() {
        try {
            return mapper.fromService(service.readAll());
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "AUTHOR_CREATE")
    @Override
    public AuthorControllerResponseDto create(AuthorControllerRequestDto createRequest) {
        try {
            AuthorServiceRequestDto serviceRequestDto = mapper.toService(createRequest);
            AuthorServiceResponseDto authorServiceResponseDto = service.create(serviceRequestDto);
            return mapper.fromService(authorServiceResponseDto);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "AUTHOR_GET_BY_ID")
    @Override
    public AuthorControllerResponseDto readById(Long id) {
        try {
            return mapper.fromService(service.readById(id));
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "AUTHOR_UPDATE")
    @Override
    public AuthorControllerResponseDto update(AuthorControllerRequestDto updateRequest) {
        try {
            AuthorServiceRequestDto serviceRequestDto = mapper.toService(updateRequest);
            AuthorServiceResponseDto authorServiceResponseDto = service.update(serviceRequestDto);
            return mapper.fromService(authorServiceResponseDto);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }

    @CommandHandler(value = "AUTHOR_REMOVE_BY_ID")
    @Override
    public boolean deleteById(Long id) {
        try {
            return service.deleteById(id);
        } catch (UnifiedServiceException e) {
            throw new UnifiedControllerException(e);
        }
    }
}
