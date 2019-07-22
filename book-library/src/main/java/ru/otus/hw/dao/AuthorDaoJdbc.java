package ru.otus.hw.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Author;
import ru.otus.hw.mapper.AuthorMapper;
import ru.otus.hw.service.OutputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;
    private final OutputService outputService;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc, OutputService outputService) {
        this.jdbc = jdbc;
        this.outputService = outputService;
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
        } catch (
        DataAccessException ex) {
            outputService.println("Author with id " + id + " not found.");
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
            outputService.println("Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName() + " inserted.");
        } catch (DataIntegrityViolationException ex) {
            outputService.println("Error insert Author with firstName: " + author.getFirstName() + ", lastName: " + author.getLastName());
        }
    }

}
