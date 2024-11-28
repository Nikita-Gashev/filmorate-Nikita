package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Film add(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Film name should not be empty");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Maximum description length - 200 symbols");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28", DateFormatter.DATE_FORMATTER))) {
            throw new ValidationException("Release date should be after 1895-12-28");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Film duration should be positive");
        }
        filmStorage.add(film);
        log.info("Film '{}' added", film.getName());
        return film;
    }

    public Film update(Film film) {
        getById(film.getId());
        filmStorage.update(film);
        log.info("Film '{}' update", film.getName());
        return film;
    }

    public Film getById(int id) {
        return filmStorage.getById(id).orElseThrow(() -> new FilmNotFoundException("Film not found"));
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film userSetLike(int userId, int filmId) {
        userService.getById(userId);
        getById(filmId).getLikes().add(userId);
        log.info("User '{}' set like '{}'", userService.getById(userId).getLogin(), getById(filmId).getName());
        return getById(filmId);
    }

    public Film userRemoveLike(int userId, int filmId) {
        userService.getById(userId);
        getById(filmId).getLikes().remove(userId);
        log.info("User '{}' remove like '{}'", userService.getById(userId).getLogin(), getById(filmId).getName());
        return getById(filmId);
    }

    public List<Film> getPopularFilms(int count) {
        if (count < 0) {
            throw new IncorrectParameterException("Count should be positive");
        }
        return filmStorage.getAll().stream()
                .sorted((f0, f1) -> Integer.compare(f1.getLikes().size(), f0.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
