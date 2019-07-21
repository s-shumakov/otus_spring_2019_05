package ru.otus.hw.dao;

import ru.otus.hw.domain.Author;

import java.util.List;

public interface AuthorDao {
    Integer count();

    List<Author> findAll();

    Author findById(Long id);

    void insert(Author author);
}
