package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    @Override
    public void addFriend(int id, int friendId) {
        userDbStorage.getUser(id);
        userDbStorage.getUser(friendId);
        String sql = "insert into friendship(user_id, friend_id, friend_confirm) values (?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, false);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        userDbStorage.getUser(id);
        userDbStorage.getUser(friendId);
        String sql = "delete from friendship where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public List<User> getFriends(int id) {
        userDbStorage.getUser(id);
        String sql = "select friend_id, email, login, name, birthday from friendship as f " +
                "join users as u on f.friend_id = u.user_id where f.user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);
    }

    private User makeUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("friend_id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));
        user.setName(rs.getString("name"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
