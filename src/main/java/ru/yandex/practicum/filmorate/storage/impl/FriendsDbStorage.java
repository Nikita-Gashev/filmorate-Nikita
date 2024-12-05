package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addFriend(User user, int friendId) {
        String sql = "insert into friends(user_id, friend_id)"
                + "values (?, ?)";
        jdbcTemplate.update(sql, user.getId(), friendId);
        user.setFriends(getFriendsId(user.getId()));
        return user;
    }

    @Override
    public User removeFriend(User user, int friendId) {
        String sql = "delete from friends where friend_id = ?";
        jdbcTemplate.update(sql, friendId);
        user.setFriends(getFriendsId(user.getId()));
        return user;
    }

    @Override
    public User getUserWithFriends(User user) {
        user.setFriends(getFriendsId(user.getId()));
        return user;
    }

    private Set<Integer> getFriendsId(int userId) {
        String sql = "select distinct friend_id from friends where user_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) ->
                getFriendId(rs), userId));
    }

    private Integer getFriendId(ResultSet rs) throws SQLException {
        return rs.getInt("friend_id");
    }
}
