package ru.otus.hw.repostory;

import ru.otus.hw.domain.Genre;

import java.util.List;

public interface GenreRepository {
    Long count();

    List<Genre> findAll();

    Genre findById(Long id);

    void insert(Genre genre);
}
