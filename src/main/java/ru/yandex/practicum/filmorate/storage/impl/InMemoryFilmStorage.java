package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public List<Film> getAll() {
        if (films.isEmpty()) {
            return Collections.emptyList();
        }
        List<Film> filmsList = new ArrayList<>();
        for (Integer id : films.keySet()) {
            filmsList.add(id - 1, films.get(id));
        }
        return filmsList;
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film add(Film film) {
        id++;
        film.setId(id);
        films.put(id, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }
}
