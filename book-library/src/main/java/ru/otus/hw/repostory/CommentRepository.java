package ru.otus.hw.repostory;

import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;

import java.util.List;

public interface CommentRepository {
    Long count();

    List<Comment> findAll();

    Comment findById(Long id);

    List<Comment> findByBook(Book book);

    void insert(Comment comment);
}
