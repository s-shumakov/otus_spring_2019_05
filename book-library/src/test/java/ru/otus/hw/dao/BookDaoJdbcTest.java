package ru.otus.hw.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Book;
import ru.otus.hw.service.OutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({BookDaoJdbc.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookDaoJdbcTest {
    @Autowired
    private BookDao bookDao;
    @MockBean
    OutputService outputService;
    @MockBean
    InputStream inputStream;
    @MockBean
    PrintStream printStream;


    @Test
    public void count() {
        int result = bookDao.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Book> books = bookDao.findAll();
        assertThat(books.size()).isEqualTo(bookDao.count());
    }

    @Test
    public void findById() {
        Book book = bookDao.findById(1L);
        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test
    public void findByIdError() {
        Book book = bookDao.findById(0L);
        assertThat(book).isNull();
    }

    @Test
    public void insert() {
        Book book = new Book("name");
        int beforeCount = bookDao.count();
        bookDao.insert(book);
        assertThat(bookDao.count()).isEqualTo(beforeCount + 1);
    }
}
