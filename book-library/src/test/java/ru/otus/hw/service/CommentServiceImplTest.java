package ru.otus.hw.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.repostory.BookRepository;
import ru.otus.hw.repostory.BookRepositoryJpa;
import ru.otus.hw.repostory.CommentRepositoryJpa;

import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({CommentRepositoryJpa.class, BookRepositoryJpa.class, CommentServiceImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentServiceImplTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BookRepository bookRepository;
    @MockBean
    OutputService outputService;
    @MockBean
    InputStream inputStream;
    @MockBean
    PrintStream printStream;

    @Test
    public void addAndGetComment() {
        Book book = bookRepository.findById(1L);
        Comment comment = new Comment("Test comment", book);
        commentService.addComment(comment);
        assertThat(commentService.getBookComments(book).get(0).getComment()).isEqualTo("Test comment");
    }

}