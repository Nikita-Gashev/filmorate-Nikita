package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void add(User user) {
        userStorage.add(user);
    }

    public void update(User user) {
        userStorage.update(user);
    }

    public User getById(int id) {
        return userStorage.getById(id);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public List<User> getUserFriends(int id) {
        List<Integer> userFriendsId = new ArrayList<>(userStorage.getById(id).getFriends());
        List<User> userFriends = new ArrayList<>();
        userFriendsId.forEach((i -> userFriends.add(userStorage.getById(i))));
        return userFriends;
    }

    public void addFriend(int userId, int userFriendId) {
        userStorage.getById(userId).getFriends().add(userFriendId);
        userStorage.getById(userFriendId).getFriends().add(userId);
    }

    public void removeFriend(int userId, int userFriendId) {
        userStorage.getById(userId).getFriends().remove(userFriendId);
        userStorage.getById(userFriendId).getFriends().remove(userId);
    }

    public List<User> getCommonFriends(int userId, int userFriendId) {
        List<Integer> commonFriendsId = new ArrayList<>(userStorage.getById(userId).getFriends());
        commonFriendsId.retainAll(userStorage.getById(userFriendId).getFriends());
        List<User> commonFriends = new ArrayList<>();
        commonFriendsId.forEach(id -> commonFriends.add(userStorage.getById(id)));
        return commonFriends;
    }
}
