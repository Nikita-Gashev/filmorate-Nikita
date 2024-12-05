package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmWithGenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilmWithGenreDbStorage implements FilmWithGenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmWithGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film setFilmWithGenre(Film film) {
        String sql = "insert into film_genre(film_id, genre_id)"
                + "values (?, ?)";
        film.getGenres().forEach(genreId -> jdbcTemplate.update(sql, film.getId(), genreId));
        return film;
    }

    @Override
    public Film updateFilmWithGenre(Film film) {
        String sql = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sql, film.getId());
        return setFilmWithGenre(film);
    }

    @Override
    public Film getFilmWithGenresId(Film film) {
        String sql = "select genre_id from film_genre where film_id = ?";
        film.setGenres(jdbcTemplate.query(sql, (rs, rowNum) -> getGenreId(rs), film.getId()));
        return film;
    }

    @Override
    public List<Genre> getGenresByFilmId(int filmId) {
        String sql = "select * from film_genre as fg join genre as g on fg.genre_id = g.genre_id where film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getGenre(rs), filmId);
    }

    private Integer getGenreId(ResultSet rs) throws SQLException {
        return rs.getInt("genre_id");
    }

    private Genre getGenre(ResultSet rs) throws SQLException {
        int genreId = rs.getInt("genre_id");
        String name = rs.getString("name");
        return new Genre(genreId, name);
    }
}
