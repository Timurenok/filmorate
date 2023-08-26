package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    void postUser(User user);
    void putUser(User user);
    List<User> getUsers();
    User getUser(int id);
}
