package ru.yandex.practicum.filmorate.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int id, int userId) {
        String sql = "insert into film_likes(film_id, user_id) values (?, ?)";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        String sql = "delete from film_likes where film_id = ? and user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    @Override
    public Set<Integer> getFilmLikes(int id) {
        String sql = "select * from film_likes where film_id = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, id);
        if (filmRows.next()) {
            return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs), id));
        }
        return new HashSet<>();
    }

    private Integer makeLike(ResultSet rs) throws SQLException {
        return rs.getInt("user_id");
    }
}
