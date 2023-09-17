package com.mjc.school.controller;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoResponse, NewsModel, Long> {
    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @Override
    public List<NewsModel> readAll() {
        return service.readAll();
    }

    @Override
    public NewsModel readById(Long id) {
        return service.readById(id);
    }

    @Override
    public NewsModel create(NewsDtoResponse createRequest) {
        return service.create(createRequest);
    }

    @Override
    public NewsModel update(NewsDtoResponse updateRequest) {
        return service.update(updateRequest);
    }

    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }
}

