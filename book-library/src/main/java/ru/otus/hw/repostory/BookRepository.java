package ru.otus.hw.repostory;

import ru.otus.hw.domain.Book;

import java.util.List;

public interface BookRepository {
    Long count();

    List<Book> findAll();

    Book findById(Long id);

    void insert(Book book);
}
