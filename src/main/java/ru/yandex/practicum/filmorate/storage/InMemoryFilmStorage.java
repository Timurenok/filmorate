package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final int MAX_LENGTH = 200;
    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);
    private static int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    public void postFilm(Film film) {
        checkFilm(film);
        film.setId(generateId());
        films.put(film.getId(), film);
    }

    public void putFilm(Film film) {
        checkFilm(film);
        if (films.get(film.getId()) == null) {
            throw new UnknownFilmException(String.format("Фильма с идентификатором %d не существует.", film.getId()));
        }
        films.put(film.getId(), film);
    }

    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    public Film getFilm(int id) {
        if (films.get(id) != null) {
            return films.get(id);
        }
        throw new UnknownFilmException(String.format("Фильма с идентификатором %d не существует.", id));
    }

    private void checkFilm(Film film) {
        if (film.getName().isBlank() || film.getName() == null) {
            throw new InvalidNameException("Название не может быть пустым.");
        }
        if (film.getDescription().length() > MAX_LENGTH) {
            throw new InvalidSizeException("Максимальная длина описания — 200 символов.");
        }
        if (film.getReleaseDate().isBefore(EARLIEST_DATE)) {
            throw new InvalidDateException("Дата релиза дожна быть не раньше 28 декабря 1895 года.");
        }
        if (film.getDuration() < 0) {
            throw new InvalidDurationException("Продолжительность фильма должна быть положительной.");
        }
    }

    private int generateId() {
        return id++;
    }
}
