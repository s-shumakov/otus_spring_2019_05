package ru.otus.hw.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({AuthorDaoJdbc.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    public void count() {
        int result = authorDao.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Author> authors = authorDao.findAll();
        assertThat(authors.size()).isEqualTo(authorDao.count());
    }

    @Test
    public void findById() {
        Author author = authorDao.findById(1L);
        assertThat(author.getId()).isEqualTo(1L);
    }

    @Test
    public void insert() {
        Author author = new Author("first_name", "last_name");
        int beforeCount = authorDao.count();
        authorDao.insert(author);
        assertThat(authorDao.count()).isEqualTo(beforeCount + 1);
    }
}