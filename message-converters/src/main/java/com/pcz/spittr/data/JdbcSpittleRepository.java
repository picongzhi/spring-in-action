package com.pcz.spittr.data;

import com.pcz.spittr.Spittle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * @author picongzhi
 */
@Repository
public class JdbcSpittleRepository implements SpittleRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcSpittleRepository(JdbcOperations jdbcOperations) {
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
                "SELECT id, message, created_at, latitude, longitude FROM Spittle WHERE id < ? ORDER BY created_at DESC LIMIT ?",
                new SpittleRowMapper(),
                max, count);
    }

    @Override
    public Spittle findOne(long id) {
        return jdbcOperations.queryForObject(
                "SELECT id, message, created_at, latitude, longitude FROM Spittle WHERE id = ?",
                new SpittleRowMapper(),
                id);
    }

    @Override
    public Spittle save(Spittle spittle) {
        String sql = "INSERT INTO Spittle (message, created_at, latitude, longitude) VALUES (?, ?, ?, ?)";
        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(sql,
                Types.VARCHAR, Types.TIMESTAMP, Types.DOUBLE, Types.DOUBLE);
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(new Object[] {
                spittle.getMessage(),
                spittle.getTime(),
                spittle.getLatitude(),
                spittle.getLongitude()
        });

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(preparedStatementCreator, generatedKeyHolder);
        return new Spittle(
                generatedKeyHolder.getKey().longValue(),
                spittle.getMessage(),
                spittle.getTime(),
                spittle.getLongitude(),
                spittle.getLatitude());
    }

    private static class SpittleRowMapper implements RowMapper<Spittle> {
        @Override
        public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Spittle(rs.getLong("id"),
                    rs.getString("message"),
                    rs.getDate("created_at"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude"));
        }
    }
}
