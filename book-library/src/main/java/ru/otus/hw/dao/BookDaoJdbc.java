package ru.otus.hw.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.domain.Book;
import ru.otus.hw.exception.DataInsertException;
import ru.otus.hw.exception.NotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.service.OutputService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final OutputService outputService;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, OutputService outputService) {
        this.jdbc = jdbc;
        this.outputService = outputService;
    }

    @Override
    public Integer count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public List<Book> findAll() {
        return jdbc.getJdbcOperations().query("select * from books b " +
                "left join authors a on b.author_id = a.id " +
                "left join genres g on b.genre_id = g.id", new BookMapper());
    }

    @Override
    public Book findById(Long id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        Book book = null;
        try {
            book = jdbc.queryForObject("select * from books b " +
                    "left join authors a on b.author_id = a.id " +
                    "left join genres g on b.genre_id = g.id " +
                    "where b.id = :id", params, new BookMapper());
        } catch (DataAccessException ex) {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
        return book;
    }

    @Override
    public void insert(Book book) {
        try {
            jdbc.getJdbcOperations().update("insert into books values (?, ?, ?, ?)",
                    null,
                    book.getName(),
                    book.getAuthor() != null ? book.getAuthor().getId() : null,
                    book.getGenre() != null ? book.getGenre().getId() : null);
        } catch (DataIntegrityViolationException ex) {
            throw new DataInsertException("Error insert Book with name: " + book.getName());
        }
    }

}
