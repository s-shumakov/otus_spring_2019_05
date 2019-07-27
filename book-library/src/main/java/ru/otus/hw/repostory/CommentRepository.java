package ru.otus.hw.repostory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);
}
