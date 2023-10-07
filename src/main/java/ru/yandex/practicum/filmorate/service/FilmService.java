package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;

    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage, LikeStorage likeStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int id, int userId) {
        filmStorage.getFilm(id);
        userStorage.getUser(userId);
        likeStorage.addLike(id, userId);
    }

    public void deleteLike(int id, int userId) {
        filmStorage.getFilm(id);
        userStorage.getUser(userId);
        likeStorage.deleteLike(id, userId);
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(toList());
    }

    public void createFilm(Film film) {
        filmStorage.createFilm(film);
    }

    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(int id) {
        return filmStorage.getFilm(id);
    }
}
