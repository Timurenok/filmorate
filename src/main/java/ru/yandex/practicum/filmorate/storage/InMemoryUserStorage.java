package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Component("inMemoryUserStorage")
public class InMemoryUserStorage implements UserStorage {
    private static final LocalDate DATE_NOW = LocalDate.now();
    private static int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void createUser(User user) {
        checkUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
    }

    @Override
    public void updateUser(User user) {
        checkUser(user);
        if (users.get(user.getId()) == null) {
            throw new UnknownUserException(String.format("Пользователя с идентификатором %d не существует.",
                    user.getId()));
        }
        users.put(user.getId(), user);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int id) {
        return Optional.ofNullable(users.get(id))
                .orElseThrow(() -> new UnknownFilmException(String.format(
                        "Пользователя с идентификатором %d не существует.", id)));
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

    private int generateId() {
        return id++;
    }
}
