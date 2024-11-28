package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public List<User> getAll() {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }
        List<User> usersList = new ArrayList<>();
        for (Integer id : users.keySet()) {
            usersList.add(id - 1, users.get(id));
        }
        return usersList;
    }

    @Override
    public Optional<User> getById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User add(User user) {
        id++;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
