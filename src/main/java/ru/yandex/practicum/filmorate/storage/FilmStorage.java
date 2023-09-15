package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void createFilm(Film film);

    void updateFilm(Film film);

    List<Film> getFilms();

    Film getFilm(int id);
}
