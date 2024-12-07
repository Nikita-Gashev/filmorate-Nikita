package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.*;

class FilmServiceTest {
/*
    UserService userService = new UserService(new InMemoryUserStorage());
    FilmService filmService = new FilmService(new InMemoryFilmStorage(), userService);

    @Test
    @DisplayName("Проверка сортировки")
    void getPopularFilms() {
        Film film1 = new Film("Name1", "Description1", "2000-01-01", 100);
        Film film2 = new Film("Name2", "Description2", "2001-01-01", 90);
        Film film3 = new Film("Name3", "Description3", "1998-01-01", 190);
        filmService.add(film1);
        filmService.add(film2);
        filmService.add(film3);
        User user1 = new User("Email1@email", "Login1", "Name1", "2000-01-01");
        User user2 = new User("Email2@email", "Login2", "Name2", "2000-01-01");
        userService.add(user1);
        userService.add(user2);
        filmService.userSetLike(user1.getId(), film1.getId());
        filmService.userSetLike(user1.getId(), film2.getId());
        filmService.userSetLike(user1.getId(), film3.getId());
        filmService.userSetLike(user2.getId(), film2.getId());
        assertEquals(film2, filmService.getPopularFilms(2).get(0), "Фильмы отсортированы некорректно");
    }*/

}