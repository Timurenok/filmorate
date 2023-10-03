package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    void addGenres(Film film);
    void deleteGenres(Film film);
    Genre getGenre(int id);
    List<Genre> getGenres();
    List<Genre> getGenresByFilmId(int filmId);
}
