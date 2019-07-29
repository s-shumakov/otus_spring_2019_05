package ru.otus.hw.service;

import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.domain.Genre;

import java.util.List;

public interface LibraryFacadeService {
    List<Author> findAllAuthors();

    List<Genre> findAllGenres();

    List<Book> findAllBooks();

    Author findAuthorById(Long id);

    Genre findGenreById(Long id);

    Book findBookById(Long id);

    void saveAuthor(Author author);

    void saveGenre(Genre genre);

    void saveBook(Book book);

    void saveComment(Comment comment);

    List<Comment> findCommentsByBook(Book book);

}
