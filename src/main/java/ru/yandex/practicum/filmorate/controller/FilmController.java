package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Request received GET /films");
        return filmService.getAll();
    }

    @PostMapping
    public Film add(@RequestBody Film film) {
        log.info("Request received POST /films");
        filmService.add(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Request received PUT /films");
        filmService.update(film);
        return film;
    }

    @GetMapping("/{id}")
    public Film getBiId(@PathVariable Integer id) {
        log.info("Request received GET /films/{}", String.valueOf(id));
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film userSetLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Request received PUT /films/{}/like/{}", String.valueOf(id), String.valueOf(userId));
        return filmService.userSetLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film userRemoveLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Request received DELETE /films/{}/like/{}", String.valueOf(id), String.valueOf(userId));
        return filmService.userRemoveLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Request received GET /popular");
        return filmService.getPopularFilms(count);
    }
}
