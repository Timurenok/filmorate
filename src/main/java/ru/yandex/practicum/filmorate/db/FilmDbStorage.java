package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikeStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component("filmDbStorage")
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private static final int MAX_LENGTH = 200;
    private static final LocalDate EARLIEST_DATE = LocalDate.of(1895, 12, 28);
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;
    private final LikeStorage likeStorage;

    @Override
    public void createFilm(Film film) {
        checkFilm(film);
        String sql = "insert into films(name, description, release_date, duration, rating_id) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getName(),
                                film.getDescription(),
                                film.getReleaseDate(),
                                film.getDuration(),
                                film.getMpa().getId());
        film.setId(getFilms().size());
        genreStorage.addGenres(film);
    }

    @Override
    public void updateFilm(Film film) {
        checkFilm(film);
        String sql = "update films set name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?" +
                " where film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", film.getId());
        if (!filmRows.next()) {
            throw new UnknownFilmException(String.format("Фильма с идентификатором %d не существует.", film.getId()));
        }
        jdbcTemplate.update(sql, film.getName(),
                                film.getDescription(),
                                film.getReleaseDate(),
                                film.getDuration(),
                                film.getMpa().getId(),
                                film.getId());
        if (film.getGenres() != null) {
            film.setGenres(new ArrayList<>(new HashSet<>(film.getGenres())).stream()
                    .sorted(Comparator.comparingInt(Genre::getId)).collect(Collectors.toList()));
        }
        genreStorage.deleteGenres(film);
        genreStorage.addGenres(film);
    }

    @Override
    public List<Film> getFilms() {
        List<Film> films = new ArrayList<>();
        String sql = "select * from films";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql);
        while (filmRows.next()) {
            Film film = new Film();
            film.setId(filmRows.getInt("film_id"));
            film.setName(filmRows.getString("name"));
            film.setDescription(filmRows.getString("description"));
            film.setReleaseDate(Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate());
            film.setDuration(filmRows.getInt("duration"));
            film.setMpa(ratingStorage.getRating(filmRows.getInt("rating_id")));
            film.setGenres(genreStorage.getGenresByFilmId(film.getId()));
            film.setUsersWhoLiked(likeStorage.getFilmLikes(film.getId()));
            films.add(film);
        }
        return films;
    }

    @Override
    public Film getFilm(int id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from films where film_id = ?", id);
        Film film = new Film();
        if (filmRows.next()) {
            film.setId(filmRows.getInt("film_id"));
            film.setName(filmRows.getString("name"));
            film.setDescription(filmRows.getString("description"));
            film.setReleaseDate(Objects.requireNonNull(filmRows.getDate("release_date")).toLocalDate());
            film.setDuration(filmRows.getInt("duration"));
            film.setMpa(ratingStorage.getRating(filmRows.getInt("rating_id")));
            film.setGenres(genreStorage.getGenresByFilmId(film.getId()));
            film.setUsersWhoLiked(likeStorage.getFilmLikes(id));
            return film;
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
}
