package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public List<Film> getAll() {
        List<Film> filmsList = new ArrayList<>();
        for (Integer id : films.keySet()) {
            filmsList.add(id - 1, films.get(id));
        }
        return filmsList;
    }

    @Override
    public Film getById(int id) {
        if (!(films.containsKey(id))) {
            throw new FilmNotFoundException("Film not found");
        }
        return films.get(id);
    }

    @Override
    public void add(Film film) {
        id++;
        film.setId(id);
        films.put(id, film);
    }

    @Override
    public void update(Film film) {
        if (!(films.containsKey(film.getId()))) {
            throw new FilmNotFoundException("Film not found");
        }
        films.put(film.getId(), film);
    }
}
