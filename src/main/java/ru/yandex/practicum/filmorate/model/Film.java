package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.UnknownUserException;

import java.time.LocalDate;
import java.util.*;

@Data
public class Film {
    private int id;
    private  String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Rating mpa;
    private Set<Integer> usersWhoLiked = new HashSet<>();
    private List<Genre> genres = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && duration == film.duration && Objects.equals(name, film.name) && Objects.equals(description, film.description) && Objects.equals(releaseDate, film.releaseDate) && Objects.equals(mpa, film.mpa) && Objects.equals(usersWhoLiked, film.usersWhoLiked) && Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, releaseDate, duration, mpa, usersWhoLiked, genres);
    }
}
