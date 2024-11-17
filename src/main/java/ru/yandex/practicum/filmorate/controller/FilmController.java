package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @GetMapping
    public List<Film> getAll() {
        List<Film> filmsList = new ArrayList<>();
        for (Integer id : films.keySet()) {
            filmsList.add(id - 1, films.get(id));
        }
        return filmsList;
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
        id++;
        film.setId(id);
        films.put(id, film);
        log.info("Film '{}' added", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!(films.containsKey(film.getId()))) {
            throw new ValidationException("Film not found");
        }
        films.put(film.getId(), film);
        log.info("Film '{}' update", film.getName());
        return film;
    }
}
