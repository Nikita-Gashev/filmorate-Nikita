package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    List<User> getAll();

    Optional<User> getById(int id);

    User add(User user);

    User update(User user);
}
