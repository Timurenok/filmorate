package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class User {
    private int id;
    @Email
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friendsId = new HashSet<>();

    public void addFriendId(int id) {
        friendsId.add(id);
    }

    public void deleteFriendId(int id) {
        friendsId.remove(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday) && Objects.equals(friendsId, user.friendsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, login, name, birthday, friendsId);
    }
}
