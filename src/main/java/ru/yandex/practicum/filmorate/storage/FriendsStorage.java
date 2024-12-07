package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface FriendsStorage {

    User addFriend(User user, int friendId);

    User removeFriend(User user, int friendId);

    User getUserWithFriends(User user);
}
