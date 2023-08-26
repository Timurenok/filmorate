package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void postFilm(Film film);

    void putFilm(Film film);

    List<Film> getFilms();

    Film getFilm(int id);
}
