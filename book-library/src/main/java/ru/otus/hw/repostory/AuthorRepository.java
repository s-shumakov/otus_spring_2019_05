package ru.otus.hw.repostory;

import ru.otus.hw.domain.Author;

import java.util.List;

public interface AuthorRepository {
    Long count();

    List<Author> findAll();

    Author findById(Long id);

    void insert(Author author);
}
