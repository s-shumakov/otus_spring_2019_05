package ru.otus.hw.dao;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Author;
import ru.otus.hw.mapper.AuthorMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Integer count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from authors", Integer.class);
    }

    @Override
    public List<Author> findAll() {
        return jdbc.getJdbcOperations().query("select * from authors", new AuthorMapper());
    }

    @Override
    public Author findById(Long id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("select * from authors where id = :id", params, new AuthorMapper());
    }

    @Override
    public void insert(Author author) {
        try {
            jdbc.getJdbcOperations().update("insert into authors values (?, ?, ?)",
                    null,
                    author.getFirstName(),
                    author.getLastName());
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
    }

}
