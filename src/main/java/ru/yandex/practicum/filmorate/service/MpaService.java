package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;

import java.util.List;

@Service
@Slf4j
public class MpaService {

    private final FilmMpaStorage filmMpaStorage;

    public MpaService(FilmMpaStorage filmMpaStorage) {
        this.filmMpaStorage = filmMpaStorage;
    }

    public List<Mpa> getAll() {
        log.info("Get list of mpa");
        return filmMpaStorage.getAll();
    }

    public Mpa getById(int mpaId) {
        try {
            log.info("Get mpa with id: {}", mpaId);
            return filmMpaStorage.getById(mpaId);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("Mpa not found");
        }
    }
}
