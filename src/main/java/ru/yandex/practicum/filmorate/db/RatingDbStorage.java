package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UnknownRatingException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Rating getRating(int id) {
        String sql = "select * from rating where rating_id = ?";
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet(sql, id);
        if (ratingRows.next()) {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> makeRating(rs), id);
        }
        throw new UnknownRatingException(String.format("Рейтнига с идентификатором %d не существует", id));
    }

    @Override
    public List<Rating> getRatings() {
        String sql = "select * from rating";
        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeRating(rs)));
    }

    private Rating makeRating(ResultSet rs) throws SQLException {
        Rating rating = new Rating();
        rating.setId(rs.getInt("rating_id"));
        rating.setName(rs.getString("name"));
        return rating;
    }
}
