package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User add(User user) {
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
        userStorage.add(user);
        log.info("User '{}' added", user.getLogin());
        return user;
    }

    public User update(User user) {
        getById(user.getId());
        userStorage.update(user);
        log.info("User '{}' update", user.getLogin());
        return user;
    }

    public User getById(int id) {
        return userStorage.getById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getUserFriends(int id) {
        if (getById(id).getFriends().isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> userFriendsId = new ArrayList<>(getById(id).getFriends());
        List<User> userFriends = new ArrayList<>();
        userFriendsId.forEach(num -> userFriends.add(getById(num)));
        return userFriends;
    }

    public void addFriend(int userId, int userFriendId) {
        getById(userId).getFriends().add(userFriendId);
        getById(userFriendId).getFriends().add(userId);
        log.info("User '{}' add '{}' to friend", getById(userId).getLogin(), getById(userFriendId).getLogin());
    }

    public void removeFriend(int userId, int userFriendId) {
        getById(userId).getFriends().remove(userFriendId);
        getById(userFriendId).getFriends().remove(userId);
        log.info("User '{}' remove '{}' from friend", getById(userId).getLogin(), getById(userFriendId).getLogin());
    }

    public List<User> getCommonFriends(int userId, int userFriendId) {
        List<User> commonFriends = new ArrayList<>(getUserFriends(userId));
        commonFriends.retainAll(getUserFriends(userFriendId));
        return commonFriends;
    }
}
