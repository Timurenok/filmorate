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
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet("select name from rating where rating_id = ?", id);
        Rating rating = new Rating();
        if (ratingRows.next()) {
            rating.setId(id);
            rating.setName(ratingRows.getString("name"));
            return rating;
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
