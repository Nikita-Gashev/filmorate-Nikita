package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmLikesStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmLikesDbStorage implements FilmLikesStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmLikesDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void userSetLikes(int userId, int filmId) {
        String sql = "insert into film_likes(user_id, film_id)"
                + "values (?, ?)";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void userRemoveLikes(int userId, int filmId) {
        String sql = "delete from film_likes where user_id = ? and film_id = ?";
        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public Film getFilmWithLikes(Film film) {
        film.setLikes(getUserLikesId(film.getId()));
        return film;
    }

    private Set<Integer> getUserLikesId(int filmId) {
        String sql = "select distinct user_id from film_likes where film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) ->
                getUserId(rs), filmId));
    }

    private Integer getUserId(ResultSet rs) throws SQLException {
        return rs.getInt("user_id");
    }
}
