package ru.otus.hw.repostory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.exception.NotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void count() {
        long result = genreRepository.count();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    public void findAll() {
        List<Genre> genres = genreRepository.findAll();
        assertThat(new Long(genres.size())).isEqualTo(genreRepository.count());
    }

    @Test
    public void findById() {
        Genre genre = genreRepository.findById(1L).orElse(null);
        assertThat(genre.getId()).isEqualTo(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdError() {
        Genre genre = genreRepository.findById(0L).orElseThrow(() -> new NotFoundException(""));
    }

    @Test
    public void insert() {
        Genre genre = new Genre("genre_name");
        long beforeCount = genreRepository.count();
        genreRepository.save(genre);
        assertThat(genreRepository.count()).isEqualTo(beforeCount + 1);
    }
}
