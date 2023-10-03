package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UnknownUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public void addFriend(int id, int friendId) {
        friendshipStorage.addFriend(id, friendId);
    }

    public void deleteFriend(int id, int friendId) {
        friendshipStorage.deleteFriend(id, friendId);
    }

    public List<User> getFriends(int id) {
        return friendshipStorage.getFriends(id);
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
