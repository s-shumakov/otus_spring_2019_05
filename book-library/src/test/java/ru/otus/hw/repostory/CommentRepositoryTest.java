package ru.otus.hw.repostory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Comment;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.OutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @MockBean
    OutputService outputService;
    @MockBean
    InputStream inputStream;
    @MockBean
    PrintStream printStream;

    @Test
    public void count() {
        Book book = bookRepository.findById(1L).orElse(null);
        commentRepository.save(new Comment("Test comment", book));
        long result = commentRepository.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Comment> comments = commentRepository.findAll();
        assertThat(new Long(comments.size())).isEqualTo(commentRepository.count());
    }

    @Test
    public void findById() {
        Book book = bookRepository.findById(1L).orElse(null);
        commentRepository.save(new Comment("Test comment", book));
        Comment comment = commentRepository.findById(1L).orElse(null);
        assertThat(comment.getId()).isEqualTo(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdError() {
        Comment comment = commentRepository.findById(0L).orElseThrow(() -> new NotFoundException(""));
    }

    @Test
    public void insertAndFind() {
        Book book = bookRepository.findById(1L).orElse(null);
        Comment comment = new Comment("Test comment", book);
        commentRepository.save(comment);
        assertThat(commentRepository.findByBook(book).get(0).getComment()).isEqualTo("Test comment");
    }

}
