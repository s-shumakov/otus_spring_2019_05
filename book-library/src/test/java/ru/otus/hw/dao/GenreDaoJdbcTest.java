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
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.service.OutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@Import({GenreDaoJdbc.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GenreDaoJdbcTest {
    @Autowired
    private GenreDao genreDao;
    @MockBean
    OutputService outputService;
    @MockBean
    InputStream inputStream;
    @MockBean
    PrintStream printStream;

    @Test
    public void count() {
        int result = genreDao.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Genre> genres = genreDao.findAll();
        assertThat(genres.size()).isEqualTo(genreDao.count());
    }

    @Test
    public void findById() {
        Genre genre = genreDao.findById(1L);
        assertThat(genre.getId()).isEqualTo(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdError() {
        Genre genre = genreDao.findById(0L);
    }

    @Test
    public void insert() {
        Genre genre = new Genre("genre_name");
        int beforeCount = genreDao.count();
        genreDao.insert(genre);
        assertThat(genreDao.count()).isEqualTo(beforeCount + 1);
    }
}
