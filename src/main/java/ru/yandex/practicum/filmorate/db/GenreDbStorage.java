package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UnknownRatingException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addGenres(Film film) {
        String sql = "insert into film_genre(film_id, genre_id) values (?, ?)";
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update(sql, film.getId(), genre.getId()
                );
            }
        }
    }

    @Override
    public void deleteGenres(Film film) {
        String sql = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(sql, film.getId());
    }

    @Override
    public Genre getGenre(int id) {
        String sql = "select * from genre where genre_id = ?";
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql, id);
        if (genreRows.next()) {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeGenre(rs), id);
        }
        throw new UnknownRatingException(String.format("Рейтнига с идентификатором %d не существует", id));
    }

    @Override
    public List<Genre> getGenres() {
        String sql = "select * from genre";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs)));
    }

    public List<Genre> getGenresByFilmId(int filmId) {
        String sql = "select * from film_genre as fg join genre as g on fg.genre_id = g.genre_id " +
                "where fg.film_id = ?";
        return new ArrayList<>(new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs), filmId))).stream()
                .sorted(Comparator.comparingInt(Genre::getId)).collect(Collectors.toList());
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("genre_id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}
