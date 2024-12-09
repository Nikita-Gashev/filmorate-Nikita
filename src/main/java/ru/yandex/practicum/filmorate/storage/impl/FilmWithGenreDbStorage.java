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
    private static final String SQL_QUERY_SET = "insert into film_genre(film_id, genre_id)"
            + "values (?, ?)";
    private static final String SQL_QUERY_DELETE = "delete from film_genre where film_id = ?";
    private static final String SQL_QUERY_GET_GENRE_ID = "select genre_id from film_genre where film_id = ?";
    private static final String SQL_QUERY_GET_GENRE = "select * from film_genre as fg join genre as g " +
            "on fg.genre_id = g.genre_id where film_id = ?";

    @Autowired
    public FilmWithGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film setFilmWithGenre(Film film) {
        film.getGenres().forEach(genreId -> jdbcTemplate.update(SQL_QUERY_SET, film.getId(), genreId));
        return film;
    }

    @Override
    public Film updateFilmWithGenre(Film film) {
        jdbcTemplate.update(SQL_QUERY_DELETE, film.getId());
        return setFilmWithGenre(film);
    }

    @Override
    public Film getFilmWithGenresId(Film film) {
        film.setGenres(jdbcTemplate.query(SQL_QUERY_GET_GENRE_ID, (rs, rowNum) -> getGenreId(rs), film.getId()));
        return film;
    }

    @Override
    public List<Genre> getGenresByFilmId(int filmId) {
        return jdbcTemplate.query(SQL_QUERY_GET_GENRE, (rs, rowNum) -> getGenre(rs), filmId);
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
