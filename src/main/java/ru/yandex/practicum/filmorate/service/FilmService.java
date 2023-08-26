package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FilmService {
    public FilmStorage filmStorage;

    @Autowired
    public FilmService() {
        filmStorage = new InMemoryFilmStorage();
    }

    public void addLike(int id, int userId) {
        filmStorage.getFilm(id).addLike(userId);

    }

    public void deleteLike(int id, int userId) {
        filmStorage.getFilm(id).deleteLike(userId);
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikes).reversed())
                .limit(count)
                .collect(toList());
    }
}
