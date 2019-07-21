package ru.otus.hw.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        final Long id = resultSet.getLong("id");
        final String firstName = resultSet.getString("first_name");
        final String lastName = resultSet.getString("last_name");
        return new Author(id, firstName, lastName);
    }
}
