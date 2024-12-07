package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
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
    public List<FilmDto> getAll() {
        log.info("Request received GET /films");
        return filmService.getAll();
    }

    @PostMapping
    public FilmDto add(@RequestBody FilmDto filmDto) {
        log.info("Request received POST /films");
        return filmService.add(filmDto);
    }

    @PutMapping
    public FilmDto update(@RequestBody FilmDto filmDto) {
        log.info("Request received PUT /films");
        return filmService.update(filmDto);
    }

    @GetMapping("/{id}")
    public FilmDto getById(@PathVariable Integer id) {
        log.info("Request received GET /films/{}", String.valueOf(id));
        return filmService.getById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public FilmDto userSetLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Request received PUT /films/{}/like/{}", String.valueOf(id), String.valueOf(userId));
        return filmService.userSetLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public FilmDto userRemoveLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Request received DELETE /films/{}/like/{}", String.valueOf(id), String.valueOf(userId));
        return filmService.userRemoveLike(userId, id);
    }

    @GetMapping("/popular")
    public List<FilmDto> getPopularFilms(@RequestParam(defaultValue = "10", required = false) int count) {
        log.info("Request received GET /popular");
        return filmService.getPopularFilms(count);
    }
}
