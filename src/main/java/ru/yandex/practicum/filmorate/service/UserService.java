package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private final FriendsStorage friendsStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public User add(User user) {
        validation(user);
        userStorage.add(user);
        log.info("User '{}' added", user.getLogin());
        return user;
    }

    private void validation(User user) {
        if (!(user.getEmail().contains("@"))) {
            throw new ValidationException("Email should contain @");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login should not contain blank");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    public User update(User user) {
        getById(user.getId());
        userStorage.update(user);
        friendsStorage.getUserWithFriends(user);
        log.info("User '{}' update", user.getLogin());
        return user;
    }

    public User getById(int id) {
        try {
            User user = userStorage.getById(id).get();
            friendsStorage.getUserWithFriends(user);
            log.info("Get user with id: {}", id);
            return user;
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException("User not found");
        }
    }

    public List<User> getAll() {
        log.info("Get list of users");
        return userStorage.getAll().stream()
                .map(friendsStorage::getUserWithFriends)
                .collect(Collectors.toList());
    }

    public List<User> getUserFriends(int id) {
        if (getById(id).getFriends().isEmpty()) {
            log.info("Get empty list of user fiends  with id: {}", id);
            return Collections.emptyList();
        }
        List<Integer> userFriendsId = new ArrayList<>(getById(id).getFriends());
        List<User> userFriends = new ArrayList<>();
        userFriendsId.forEach(num -> userFriends.add(getById(num)));
        log.info("Get user friends with id: {}", id);
        return userFriends;
    }

    public User addFriend(int userId, int userFriendId) {
        getById(userFriendId);
        User user = friendsStorage.addFriend(getById(userId), userFriendId);
        log.info("User '{}' add '{}' to friend", getById(userId).getLogin(), getById(userFriendId).getLogin());
        return user;
    }

    public User removeFriend(int userId, int userFriendId) {
        User user = friendsStorage.removeFriend(getById(userId), userFriendId);
        log.info("User '{}' remove '{}' from friend", getById(userId).getLogin(), getById(userFriendId).getLogin());
        return user;
    }

    public List<User> getCommonFriends(int userId, int userFriendId) {
        List<User> commonFriends = new ArrayList<>(getUserFriends(userId));
        commonFriends.retainAll(getUserFriends(userFriendId));
        log.info("Get common friends between user with id:{} and user with id: {}", userId, userFriendId);
        return commonFriends;
    }
}
