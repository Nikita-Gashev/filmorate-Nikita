package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_QUERY_GET_ALL = "select * from genre";
    private static final String SQL_QUERY_GET_BY_ID = "select * from genre where genre_id = ?";
    private static final String SQL_QUERY_GET_ALL_ID = "select genre_id from genre";

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(SQL_QUERY_GET_ALL, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public Genre getById(int genreId) {
        return jdbcTemplate.queryForObject(SQL_QUERY_GET_BY_ID, (rs, rowNum) -> makeGenre(rs), genreId);
    }

    @Override
    public List<Integer> getAllGenreId() {
        return jdbcTemplate.query(SQL_QUERY_GET_ALL_ID, (rs, rowNum) -> makeGenreId(rs));
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("genre_id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }

    private Integer makeGenreId(ResultSet rs) throws SQLException {
        return rs.getInt("genre_id");
    }
}
