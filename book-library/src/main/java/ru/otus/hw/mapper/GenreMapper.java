package ru.otus.hw.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.hw.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        final Long id = resultSet.getLong("id");
        final String genreName = resultSet.getString("genre_name");
        return new Genre(id, genreName);
    }
}
