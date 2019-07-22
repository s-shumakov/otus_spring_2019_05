package ru.otus.hw.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.mapper.GenreMapper;
import ru.otus.hw.service.OutputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;
    private final OutputService outputService;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc, OutputService outputService) {
        this.jdbc = jdbc;
        this.outputService = outputService;
    }

    @Override
    public Integer count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genres", Integer.class);
    }

    @Override
    public List<Genre> findAll() {
        return jdbc.getJdbcOperations().query("select * from genres", new GenreMapper());
    }

    @Override
    public Genre findById(Long id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Genre genre = null;
        try {
            genre = jdbc.queryForObject("select * from genres where id = :id", params, new GenreMapper());
        } catch (DataAccessException ex) {
            outputService.println("Genre with id " + id + " not found.");
        }
        return genre;
    }

    @Override
    public void insert(Genre genre) {
        try {
            jdbc.getJdbcOperations().update("insert into genres values (?, ?)",
                    null,
                    genre.getGenreName());
            outputService.println("Genre with genreName: " + genre.getGenreName() + " inserted.");
        } catch (DataIntegrityViolationException ex) {
            outputService.println("Error insert Genre with name " + genre.getGenreName());
        }
    }

}
