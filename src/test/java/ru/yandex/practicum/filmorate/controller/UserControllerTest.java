package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.impl.InMemoryUserStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    private final UserController userController = new UserController(new UserService(new InMemoryUserStorage()));

    @Test
    @DisplayName("Добавление корректного пользователя")
    void add1() {
        User user = new User("Email@email", "Login", "Name", "2000-01-01");
        userController.add(user);
        assertEquals(1, userController.getAll().size(), "Пользователь не добавлен");
    }

    @Test
    @DisplayName("Выбросить исключение с пустой почтой")
    void add2() {
        User user = new User("", "Login", "Name", "2000-01-01");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.add(user);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Выбросить исключение с почтой без @")
    void add3() {
        User user = new User("Email", "Login", "Name", "2000-01-01");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.add(user);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Выбросить исключение с пустым логином")
    void add4() {
        User user = new User("Email", "", "Name", "2000-01-01");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.add(user);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Выбросить исключение с логином с пробелами")
    void add5() {
        User user = new User("Email", "Login login", "Name", "2000-01-01");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.add(user);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Выбросить исключение с датой рождения в будущем")
    void add6() {
        User user = new User("Email", "Login login", "Name", "2100-01-01");
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> {
                    userController.add(user);
                },
                "Исключение не выброшено"
        );
    }

    @Test
    @DisplayName("Подстановка логина вместо пустого имени")
    void add7() {
        User user = new User("Email@email", "Login", "", "2000-01-01");
        userController.add(user);
        assertEquals("Login", userController.getAll().get(1).getName(), "Имя не совпадает");
    }
}