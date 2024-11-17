package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28", formatter))) {
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
        }
        id++;
        film.setId(id);
        films.put(id, film);
        log.info("Фильм '{}' добавлен", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (!(films.containsKey(film.getId()))) {
            throw new ValidationException("Фильм не найден");
        }
        films.put(film.getId(), film);
        log.info("Фильм '{}' обновлен", film.getName());
        return film;
    }
}
