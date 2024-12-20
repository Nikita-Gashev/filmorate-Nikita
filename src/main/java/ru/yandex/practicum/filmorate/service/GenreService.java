package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getAll() {
        log.info("Get list of genres");
        return genreStorage.getAll();
    }

    public Genre getById(int genreId) {
        try {
            log.info("Get genre with id: {}", genreId);
            return genreStorage.getById(genreId);
        } catch (EmptyResultDataAccessException e) {
            throw new GenreNotFoundException("Genre not found");
        }
    }
}
