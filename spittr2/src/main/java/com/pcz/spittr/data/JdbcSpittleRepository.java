package com.pcz.spittr.data;

import com.pcz.spittr.Spittle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author picongzhi
 */
@Repository
public class JdbcSpittleRepository implements SpittleRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    private JdbcSpittleRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Spittle> findRecentSpittles() {
        return jdbcOperations.query(
                "SELECT id, message, created_at, latitude, longitude FROM Spittle ORDER BY created_at DESC LIMIT 20",
                new SpittleRowMapper());
    }

    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return jdbcOperations.query(
                "SELECT id, message, created_at, latitude, longitude FROM Spittle WHERE id < ? ORDER BY created_at DESC LIMIT 20",
                new SpittleRowMapper(), max);
    }

    @Override
    public Spittle findOne(long id) {
        List<Spittle> spittles = jdbcOperations.query(
                "SELECT id, message, created_at, latitude, longitude FROM Spittle WHERE id = ?",
                new SpittleRowMapper(), id);
        return spittles.size() > 0 ? spittles.get(0) : null;
    }

    @Override
    public void save(Spittle spittle) {
        jdbcOperations.update(
                "INSERT INTO Spittle (message, created_at, latitude, longitude) VALUES (?, ?, ?, ?)",
                spittle.getMessage(), spittle.getTime(), spittle.getLatitude(), spittle.getLongitude());
    }

    private static class SpittleRowMapper implements RowMapper<Spittle> {
        @Override
        public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Spittle(
                    rs.getLong("id"),
                    rs.getString("message"),
                    rs.getDate("created_at"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude"));
        }
    }
}
