package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmLikesStorage {

    void userSetLikes(int userId, int filmId);

    void userRemoveLikes(int userId, int filmId);

    Film getFilmWithLikes(Film film);
}
