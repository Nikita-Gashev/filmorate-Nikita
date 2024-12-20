package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilmMpaDbStorage implements FilmMpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private static final String SQL_QUERY_GET_ALL = "select * from film_mpa";
    private static final String SQL_QUERY_GET_BY_ID = "select * from film_mpa where mpa_id = ?";
    private static final String SQL_QUERY_GET_ALL_ID = "select mpa_id from film_mpa";

    @Autowired
    public FilmMpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query(SQL_QUERY_GET_ALL, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public Mpa getById(int mpaId) {
        return jdbcTemplate.queryForObject(SQL_QUERY_GET_BY_ID, (rs, rowNum) -> makeMpa(rs), mpaId);
    }

    @Override
    public List<Integer> getAllMpaId() {
        return jdbcTemplate.query(SQL_QUERY_GET_ALL_ID, (rs, rowNum) -> makeMpaId(rs));
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("mpa_id");
        String name = rs.getString("name");
        return new Mpa(id, name);
    }

    private Integer makeMpaId(ResultSet rs) throws SQLException {
        return rs.getInt("mpa_id");
    }
}
