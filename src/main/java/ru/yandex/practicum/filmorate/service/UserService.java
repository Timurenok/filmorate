package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UnknownUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addFriend(int id, int friendId) {
        User me = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        me.addFriendId(friendId);
        friend.addFriendId(id);
    }

    public void deleteFriend(int id, int friendId) {
        User me = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        me.deleteFriendId(friendId);
        friend.deleteFriendId(id);
    }

    public List<User> getFriends(int id) {
        User user = userStorage.getUser(id);
        if (user != null) {
            return userStorage.getUsers().stream()
                    .filter(u -> user.getFriendsId().contains(u.getId()))
                    .collect(toList());
        }
        throw new UnknownUserException(String.format("Пользователя с идентификатором %d не существует.", id));
    }

    public List<User> getCommonFriends(int id, int otherId) {
        User user = userStorage.getUser(id);
        User otherUser = userStorage.getUser(otherId);
        if (user != null && otherUser != null) {
            List<User> commonFriendsId = new ArrayList<>();
            if (user.getFriendsId() != null && otherUser.getFriendsId() != null) {
                for (User u : getFriends(id)) {
                    if (getFriends(otherId).contains(u)) {
                        commonFriendsId.add(u);
                    }
                }
                return commonFriendsId;
            }
            return commonFriendsId;
        }
        throw new UnknownUserException(String.format("Пользователя с идентификатором %d не существует.", id));
    }

    public void createUser(User user) {
        userStorage.createUser(user);
    }

    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(int id) {
        return userStorage.getUser(id);
    }
}
