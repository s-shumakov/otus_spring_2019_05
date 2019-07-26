package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.repostory.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        this.commentRepository.insert(comment);
    }

    @Override
    public List<Comment> getBookComments(Book book) {
        return null;
    }
}
