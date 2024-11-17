package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @GetMapping
    public List<User> getAll() {
        List<User> usersList = new ArrayList<>();
        for (Integer id : users.keySet()) {
            usersList.add(id - 1, users.get(id));
        }
        return usersList;
    }

    @PostMapping
    public User add(@RequestBody User user) {
        if (user.getEmail().isBlank()) {
            throw new ValidationException("Email should not be empty");
        }
        if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("Email should contain @");
        }
        if (user.getLogin().isBlank()) {
            throw new ValidationException("Login should not be empty");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login should not be contain blank");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday should not be in the future");
        }
        id++;
        user.setId(id);
        users.put(id, user);
        log.info("User '{}' added", user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (!(users.containsKey(user.getId()))) {
            throw new ValidationException("User not found");
        }
        users.put(user.getId(), user);
        log.info("User '{}' update", user.getLogin());
        return user;
    }
}
