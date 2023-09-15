package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public User postUser(@RequestBody User user) {
        userService.createUser(user);
        log.info("Добавление пользователя {}", user);
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        userService.updateUser(user);
        log.info("Обновление пользователя {}", user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Получение пользователей {}", userService.getUsers());
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable int id) {
        log.info("Получение пользователя {}", userService.getUser(id));
        return userService.getUser(id);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Добавление друга с идентификатором {}", friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Удаление друга с идентификатором {}", friendId);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получений друзей пользователем с идентификатором {} : {}", id, userService.getFriends(id));
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получений общих друзей пользователя {} с пользователем {}", id, otherId);
        return userService.getCommonFriends(id, otherId);
    }
}
