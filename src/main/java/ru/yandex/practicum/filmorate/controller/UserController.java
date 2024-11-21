package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
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
        userService.add(user);
        log.info("User '{}' added", user.getLogin());
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        userService.update(user);
        log.info("User '{}' update", user.getLogin());
        return user;
    }

    @GetMapping("/{id}")
    public User getBiId(@PathVariable int id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
        return userService.getById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
        return userService.getById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUserFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
