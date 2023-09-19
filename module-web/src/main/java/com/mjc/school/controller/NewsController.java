package com.mjc.school.controller;

import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDto, NewsModel, Long> {
    private final NewsService service;

    public NewsController(NewsService service) {
        this.service = service;
    }

    @CommandHandler(value = "NEWS_GET_ALL")
    @Override
    public List<NewsModel> readAll() {
        return service.readAll();
    }

    @CommandHandler(value = "NEWS_CREATE")
    @Override
    public NewsModel create(NewsDto createRequest) {
        return service.create(createRequest);
    }

    @CommandHandler(value = "NEWS_GET_BY_ID")
    @Override
    public NewsModel readById(Long id) {
        return service.readById(id);
    }

    @CommandHandler(value = "NEWS_UPDATE")
    @Override
    public NewsModel update(NewsDto updateRequest) {
        return service.update(updateRequest);
    }

    @CommandHandler(value = "NEWS_REMOVE_BY_ID")
    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }
}

