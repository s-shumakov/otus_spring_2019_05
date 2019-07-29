package ru.otus.hw.service;

import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);
    List<Comment> getBookComments(Book book);
}
