package ru.otus.hw.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Author;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;
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
        Author author = null;
        try {
            author = jdbc.queryForObject("select * from authors where id = :id", params, new AuthorMapper());
        } catch (DataAccessException e) {
            throw new NotFoundException("Author with id " + id + " not found.");
        }
        return author;
    }

    @Override
    public void insert(Author author) {
        try {
            jdbc.getJdbcOperations().update("insert into authors values (?, ?, ?)",
                    null,
                    author.getFirstName(),
                    author.getLastName());
        } catch (DataIntegrityViolationException e) {
            throw new DataInsertException(
                    "Error insert Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName()
            );
        }
    }

}
