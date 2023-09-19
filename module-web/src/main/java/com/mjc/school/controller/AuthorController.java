package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController implements BaseController<AuthorDto, AuthorModel, Long> {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @CommandHandler(value = "AUTHOR_GET_ALL")
    @Override
    public List<AuthorModel> readAll() {
        return service.readAll();
    }

    @CommandHandler(value = "AUTHOR_CREATE")
    @Override
    public AuthorModel create(AuthorDto createRequest) {
        return service.create(createRequest);
    }

    @CommandHandler(value = "AUTHOR_GET_BY_ID")
    @Override
    public AuthorModel readById(Long id) {
        return service.readById(id);
    }

    @CommandHandler(value = "AUTHOR_UPDATE")
    @Override
    public AuthorModel update(AuthorDto updateRequest) {
        return service.update(updateRequest);
    }

    @CommandHandler(value = "AUTHOR_REMOVE_BY_ID")
    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }
}
