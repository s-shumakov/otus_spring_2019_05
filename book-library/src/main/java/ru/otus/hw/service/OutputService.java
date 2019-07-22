package ru.otus.hw.service;

import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;

import java.util.List;

public interface OutputService {
    void printAuthors(List<Author> authors);
    void printGenres(List<Genre> genres);
    void printBooks(List<Book> books);
    void println(String message);
}
