package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_QUERY_GET_ALL = "select * from film order by film_id";
    private static final String SQL_QUERY_GET_BY_ID = "select * from film where film_id = ?";
    private static final String SQL_QUERY_UPDATE_BY_ID = "update film set name = ?, description = ?, " +
            "release_date = ?, duration = ?, mpa_id = ? where film_id = ?";

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> getAll() {
        return jdbcTemplate.query(SQL_QUERY_GET_ALL, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_QUERY_GET_BY_ID, (rs, rowNum) -> makeFilm(rs), id));
    }

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("film")
                .usingGeneratedKeyColumns("film_id");
        film.setId(simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue());
        return film;
    }

    @Override
    public Film update(Film film) {
        jdbcTemplate.update(SQL_QUERY_UPDATE_BY_ID, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpaId(), film.getId());
        return film;
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpa = rs.getInt("mpa_id");
        return new Film(id, name, description, releaseDate, duration, mpa);
    }
}
