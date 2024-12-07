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

    @Autowired
    public FilmMpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        String sql = "select * from film_mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpa(rs));
    }

    @Override
    public Mpa getById(int mpaId) {
        String sql = "select * from film_mpa where mpa_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeMpa(rs), mpaId);
    }

    @Override
    public List<Integer> getAllMpaId() {
        String sql = "select mpa_id from film_mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMpaId(rs));
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
