package ru.yandex.practicum.filmorate.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;

import java.util.List;

@Service
public class MpaService {

    private final FilmMpaStorage filmMpaStorage;

    public MpaService(FilmMpaStorage filmMpaStorage) {
        this.filmMpaStorage = filmMpaStorage;
    }

    public List<Mpa> getAll() {
        return filmMpaStorage.getAll();
    }

    public Mpa getById(int mpaId) {
        try {
            return filmMpaStorage.getById(mpaId);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("Mpa not found");
        }
    }
}
