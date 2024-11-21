package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;
    private final UserService userService;

    public FilmController(FilmService filmService, UserService userService) {
        this.filmService = filmService;
        this.userService = userService;
    }

    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    @PostMapping
    public Film add(@RequestBody Film film) {

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
        filmService.add(film);
        log.info("Film '{}' added", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        filmService.update(film);
        log.info("Film '{}' update", film.getName());
        return film;
    }

    @GetMapping("/{id}")
    public Film getBiId(@PathVariable Integer id) {
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film userSetLike(@PathVariable int id, @PathVariable int userId) {
        userService.getById(userId);
        filmService.userSetLike(userId, id);
        return filmService.getById(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film userRemoveLike(@PathVariable int id, @PathVariable int userId) {
        userService.getById(userId);
        filmService.userRemoveLike(userId, id);
        return filmService.getById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        if (count < 0) {
            throw new IncorrectParameterException("Count should be positive");
        }
        return filmService.getPopularFilms(count);
    }
}
