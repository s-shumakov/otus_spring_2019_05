package ru.otus.hw.dao;

import ru.otus.hw.domain.Genre;

import java.util.List;

public interface GenreDao {
    Integer count();

    List<Genre> findAll();

    Genre findById(Long id);

    void insert(Genre genre);
}
