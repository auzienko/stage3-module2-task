package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.datasource.DataSource;
import com.mjc.school.repository.exception.AuthorCreationException;
import com.mjc.school.repository.exception.AuthorUpdateException;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {
    private final DataSource<AuthorModel> dataSource;
    private final AtomicLong index;

    public AuthorRepository(DataSource<AuthorModel> dataSource) {
        this.dataSource = dataSource;
        index = new AtomicLong(getMaxIndex());
    }

    private List<AuthorModel> getContent() {
        return dataSource.getContent();
    }

    private Long getMaxIndex() {
        return getContent().stream()
                .map(BaseEntity::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    @Override
    public AuthorModel create(AuthorModel object) {
        if (object == null) {
            throw new AuthorCreationException();
        }
        object.setId(index.addAndGet(1L));
        getContent().add(object);

        return readById(object.getId())
                .orElseThrow(AuthorCreationException::new);
    }

    @Override
    public List<AuthorModel> readAll() {
        List<AuthorModel> resultList = new ArrayList<>();

        getContent().forEach(e -> resultList.add(objectClone(e)));

        return resultList;
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
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

        Optional<AuthorModel> any = getContent().stream()
                .filter(e -> e.getId().equals(id)).findAny();

        return any.map(e -> getContent().remove(e))
                .orElse(false);
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        if (entity == null) {
           throw new AuthorUpdateException();
        }
        Optional<AuthorModel> current = readById(entity.getId());

        return current.map(authorModel -> contentCopy(entity, authorModel))
                .orElseThrow(AuthorUpdateException::new);
    }

    private AuthorModel contentCopy(AuthorModel from, AuthorModel to) {
        to.setName(from.getName());
        return to;
    }

    private AuthorModel objectClone(AuthorModel entity) {
        return new AuthorModel(entity);
    }
}
