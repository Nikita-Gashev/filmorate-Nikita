package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {

    /*private final FilmController filmController = new FilmController(new FilmService(new InMemoryFilmStorage(),
            new UserService(new InMemoryUserStorage())));

    @Test
    @DisplayName("Добавление корректного фильма")
    void add1() {
        Film film = new Film("Name", "Description", "2000-01-01", 100);
        filmController.add(film);
        assertEquals(1, filmController.getAll().size(), "Фильм не добавлен");
    }

    @Test
    @DisplayName("Должен выбросить исключение при добавлении фильма без названия")
    void add2() {
        Film film = new Film("", "Description", "2000-01-01", 100);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.add(film);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Должен выбросить исключение при добавлении фильма с описанием больше 200 символов")
    void add3() {
        String description = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "11111111111111";
        Film film = new Film("Name", description, "2000-01-01", 100);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.add(film);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Должен выбросить исключение при добавлении фильма выпущенного ранее 1895-12-28")
    void add4() {
        Film film = new Film("Name", "Description", "1895-12-27", 100);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.add(film);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Должен выбросить исключение при добавлении фильма с отрицательной продолжительностью")
    void add5() {
        Film film = new Film("Name", "Description", "2000-01-01", -1);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    filmController.add(film);
                },
                "Исключение не выброшено"
        );
    }*/
}