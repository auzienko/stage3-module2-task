package com.mjc.school.repository;

import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.exception.NewsCreationException;
import com.mjc.school.repository.exception.NewsUpdateException;
import com.mjc.school.repository.model.BaseEntity;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class NewsRepository implements BaseRepository<NewsModel, Long> {
    private final DataSource<NewsModel> dataSource;
    private final AtomicLong index;

    public NewsRepository(DataSource<NewsModel> dataSource) {
        this.dataSource = dataSource;
        index = new AtomicLong(getMaxIndex());
    }

    private List<NewsModel> getContent() {
        return dataSource.getContent();
    }

    private Long getMaxIndex() {
        return getContent().stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public NewsModel create(NewsModel entity) {
        if (entity == null) {
            throw new NewsCreationException();
        }
        entity.setId(index.addAndGet(1L));
        getContent().add(
                setCreateUpdateDate(entity));

        return readById(entity.getId())
                .orElseThrow(NewsCreationException::new);
    }

    @Override
    public List<NewsModel> readAll() {
        List<NewsModel> resultList = new ArrayList<>();
        getContent().forEach(e -> resultList.add(objectClone(e)));
        return resultList;
    }

    @Override
    public Optional<NewsModel> readById(Long id) {
        if (id == null) {
            return Optional.empty();
        }

        return getContent().stream()
                .filter(e -> e.getId().equals(id))
                .findAny();
    }

    @Override
    public boolean deleteById(Long id) {
        if (id == null) {
            return false;
        }
        Optional<NewsModel> any = getContent().stream()
                .filter(e -> e.getId().equals(id)).findAny();

        return any.map(e -> getContent().remove(e))
                .orElse(false);
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    @Override
    public NewsModel update(NewsModel entity) {
        if (entity == null) {
            throw new NewsUpdateException();
        }
        Optional<NewsModel> currentNews = readById(entity.getId());

        return currentNews.map(newsModel -> contentCopy(entity, newsModel))
                .orElseThrow(NewsUpdateException::new);
    }

    private NewsModel objectClone(NewsModel object) {
        return new NewsModel(object);
    }

    private NewsModel contentCopy(NewsModel from, NewsModel to) {
        to.setAuthorId(from.getAuthorId());
        to.setContent(from.getContent());
        to.setTitle(from.getTitle());
        to.setCreateDate(from.getCreateDate());

        return setCreateUpdateDate(to);
    }

    private NewsModel setCreateUpdateDate(NewsModel entity) {
        LocalDateTime now = LocalDateTime.now();
        entity.setCreateDate(entity.getCreateDate() == null ? now : entity.getCreateDate());
        entity.setLastUpdateDate(now);
        return entity;
    }
}
