package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmWithGenreStorage {

    Film setFilmWithGenre(Film film);

    Film updateFilmWithGenre(Film film);

    Film getFilmWithGenresId(Film film);

    List<Genre> getGenresByFilmId(int filmId);
}
