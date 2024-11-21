package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public List<User> getAll() {
        List<User> usersList = new ArrayList<>();
        for (Integer id : users.keySet()) {
            usersList.add(id - 1, users.get(id));
        }
        return usersList;
    }

    @Override
    public User getById(int id) {
        if (!(users.containsKey(id))) {
            throw new UserNotFoundException("User not found");
        }
        return users.get(id);
    }

    @Override
    public void add(User user) {
        id++;
        user.setId(id);
        users.put(id, user);
    }

    @Override
    public void update(User user) {
        if (!(users.containsKey(user.getId()))) {
            throw new UserNotFoundException("User not found");
        }
        users.put(user.getId(), user);
    }
}
