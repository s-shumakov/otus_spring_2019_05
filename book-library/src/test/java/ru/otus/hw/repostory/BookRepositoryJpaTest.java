package ru.otus.hw.repostory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.hw.domain.Book;
import ru.otus.hw.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BookRepositoryJpa.class})
public class BookRepositoryJpaTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void count() {
        long result = bookRepository.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Book> books = bookRepository.findAll();
        assertThat(new Long(books.size())).isEqualTo(bookRepository.count());
    }

    @Test
    public void findById() {
        Book book = bookRepository.findById(1L);
        assertThat(book.getId()).isEqualTo(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdError() {
        Book book = bookRepository.findById(0L);
        assertThat(book).isNull();
    }

    @Test
    public void insert() {
        Book book = new Book("name");
        long beforeCount = bookRepository.count();
        bookRepository.insert(book);
        assertThat(bookRepository.count()).isEqualTo(beforeCount + 1);
    }
}
