package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.UnknownUserException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;
    private  String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> usersWhoLiked = new HashSet<>();

    public void addLike(int userId) {
        usersWhoLiked.add(userId);
    }

    public void deleteLike(int userId) {
        if (usersWhoLiked.contains(userId)) {
            usersWhoLiked.remove(userId);
        }
        throw new UnknownUserException(String.format("Пользователь с идентификатором %d не ставил лайк на этот фильм.",
                id));
    }

    public int getLikes() {
        return usersWhoLiked.size();
    }
}
