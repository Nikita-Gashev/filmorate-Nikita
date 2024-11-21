package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void add(Film film) {
        filmStorage.add(film);
    }

    public void update(Film film) {
        filmStorage.update(film);
    }

    public Film getById(int id) {
        return filmStorage.getById(id);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public void userSetLike(int userId, int filmId) {
        filmStorage.getById(filmId).getLikes().add(userId);
    }

    public void userRemoveLike(int userId, int filmId) {
        filmStorage.getById(filmId).getLikes().remove(userId);
    }

    public List<Film> getPopularFilms(int size) {
        return filmStorage.getAll().stream()
                .sorted((f0, f1) -> Integer.compare(f1.getLikes().size(), f0.getLikes().size()))
                .limit(size)
                .collect(Collectors.toList());
    }
}
