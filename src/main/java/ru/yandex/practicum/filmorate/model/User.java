package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.HashSet;
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
}
