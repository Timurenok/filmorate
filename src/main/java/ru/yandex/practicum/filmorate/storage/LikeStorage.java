package ru.yandex.practicum.filmorate.storage;

import java.util.Set;

public interface LikeStorage {
    void addLike(int id, int userId);
    void deleteLike(int id, int userId);
    Set<Integer> getFilmLikes(int id);
}
