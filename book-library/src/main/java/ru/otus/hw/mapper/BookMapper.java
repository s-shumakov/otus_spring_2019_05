package ru.otus.hw.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw.dao.AuthorDao;
import ru.otus.hw.dao.GenreDao;
import ru.otus.hw.domain.Author;
import ru.otus.hw.domain.Book;
import ru.otus.hw.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        final Long id = resultSet.getLong("id");
        final String name = resultSet.getString("name");
        final Long authorId = resultSet.getLong("author_id");
        final String firstName = resultSet.getString("first_name");
        final String lastName = resultSet.getString("last_name");
        final Long genreId = resultSet.getLong("genre_id");
        final String genreName = resultSet.getString("genre_name");
        final Author author = authorId != 0 ? new Author(authorId, firstName, lastName) : null;
        final Genre genre = genreId != 0 ? new Genre(genreId, genreName) : null;
        return new Book(id, name, author, genre);
    }
}
