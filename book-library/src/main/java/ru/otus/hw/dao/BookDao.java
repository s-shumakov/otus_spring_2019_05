package ru.otus.hw.dao;

import ru.otus.hw.domain.Book;

import java.util.List;

public interface BookDao {
    Integer count();

    List<Book> findAll();

    Book findById(Long id);

    void insert(Book book);
}
