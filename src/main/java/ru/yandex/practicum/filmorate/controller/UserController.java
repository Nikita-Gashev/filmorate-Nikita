package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Request received GET /users");
        return userService.getAll();
    }

    @PostMapping
    public User add(@RequestBody User user) {
        log.info("Request received POST /users");
        userService.add(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        log.info("Request received PUT /users");
        userService.update(user);
        return user;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        log.info("Request received GET /users/{}", String.valueOf(id));
        return userService.getById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Request received PUT /users/{}/friends/{}", String.valueOf(id), String.valueOf(friendId));
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Request received DELETE /users/{}/friends/{}", String.valueOf(id), String.valueOf(friendId));
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        log.info("Request received GET /users/{}/friends", String.valueOf(id));
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUserFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Request received GET /users/{}/friends/common/{}", String.valueOf(id), String.valueOf(otherId));
        return userService.getCommonFriends(id, otherId);
    }
}
