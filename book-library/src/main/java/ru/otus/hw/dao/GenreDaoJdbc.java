package ru.otus.hw.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Genre;
import ru.otus.hw.mapper.GenreMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
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
        return jdbc.queryForObject("select * from genres where id = :id", params, new GenreMapper());
    }

    @Override
    public void insert(Genre genre) {
        try {
            jdbc.getJdbcOperations().update("insert into genres values (?, ?)",
                    null,
                    genre.getGenreName());
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
    }

}
