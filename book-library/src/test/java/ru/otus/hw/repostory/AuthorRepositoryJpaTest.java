package ru.otus.hw.repostory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.ConsoleContext;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.OutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({AuthorRepositoryJpa.class, ConsoleContext.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AuthorRepositoryJpaTest {
    @Autowired
    private AuthorRepository authorRepository;
    @MockBean
    OutputService outputService;
    @MockBean
    InputStream inputStream;
    @MockBean
    PrintStream printStream;

    @Test
    public void count() {
        long result = authorRepository.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Author> authors = authorRepository.findAll();
        assertThat(new Long(authors.size())).isEqualTo(authorRepository.count());
    }

    @Test
    public void findById() {
        Author author = authorRepository.findById(1L);
        assertThat(author.getId()).isEqualTo(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdError() {
        Author author = authorRepository.findById(0L);
        assertThat(author).isNull();
    }


    @Test
    public void insert() {
        Author author = new Author("first_name", "last_name");
        long beforeCount = authorRepository.count();
        authorRepository.insert(author);
        assertThat(authorRepository.count()).isEqualTo(beforeCount + 1);
    }
}
