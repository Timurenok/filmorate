package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component("userDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private static final LocalDate DATE_NOW = LocalDate.now();
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createUser(User user) {
        String sql = "insert into users(email, login, name, birthday) values (?, ?, ?, ?)";
        checkUser(user);
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        user.setId(getUsers().size());
    }

    @Override
    public void updateUser(User user) {
        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where user_id = ?";
        checkUser(user);
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", user.getId());
        if (!userRows.next()) {
            throw new UnknownUserException(String.format("Пользователя с идентификатором %d не существует.",
                    user.getId()));
        }
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sql);
        while (userRows.next()) {
            User user = new User();
            user.setId(userRows.getInt("user_id"));
            user.setEmail(userRows.getString("email"));
            user.setLogin(userRows.getString("login"));
            user.setName(userRows.getString("name"));
            user.setBirthday(Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate());
            users.add(user);
        }
        return users;
    }

    @Override
    public User getUser(int id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where user_id = ?", id);
        User user = new User();
        if (userRows.next()) {
            user.setId(userRows.getInt("user_id"));
            user.setEmail(userRows.getString("email"));
            user.setLogin(userRows.getString("login"));
            user.setName(userRows.getString("name"));
            user.setBirthday(Objects.requireNonNull(userRows.getDate("birthday")).toLocalDate());
            return user;
        }
        throw  new UnknownUserException(String.format("Пользователя с идентификатором %d не существует.", id));
    }

    private void checkUser(User user) {
        if (user.getEmail().isBlank() || user.getEmail() == null) {
            throw new InvalidEmailException("Электронная почта не может быть пустой.");
        }
        if (!user.getEmail().contains("@")) {
            throw new InvalidEmailException("Электронная почта должна содержать символ '@'.");
        }
        if (user.getLogin().isBlank() || user.getLogin() == null) {
            throw new InvalidLoginException("Логин не может быть пустым.");
        }
        if (user.getLogin().contains(" ")) {
            throw new InvalidLoginException("Логин не может содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(DATE_NOW)) {
            throw new InvalidDateException("Дата рождения не может быть в будущем.");
        }
    }
}
