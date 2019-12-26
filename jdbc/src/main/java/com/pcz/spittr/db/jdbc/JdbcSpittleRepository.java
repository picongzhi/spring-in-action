package com.pcz.spittr.db.jdbc;

import com.pcz.spittr.db.SpittleRepository;
import com.pcz.spittr.db.domain.Spitter;
import com.pcz.spittr.db.domain.Spittle;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author picongzhi
 */
public class JdbcSpittleRepository implements SpittleRepository {
    private static final String SELECT_SPITTLE = "SELECT sp.id, s.id as spitterId, s.username, s.password, s.fullname, s.email, s.updateByEmail, sp.message, sp.postedTime FROM Spittle sp, Spitter s WHERE sp.spitter = s.id";
    private static final String SELECT_SPITTLE_BY_ID = SELECT_SPITTLE + " and sp.id=?";
    private static final String SELECT_SPITTLES_BY_SPITTER_ID = SELECT_SPITTLE + " and s.id=? order by sp.postedTime desc";
    private static final String SELECT_RECENT_SPITTLES = SELECT_SPITTLE + " order by sp.postedTime desc limit ?";

    private JdbcTemplate jdbcTemplate;

    public JdbcSpittleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForLong("SELECT count(id) FROM Spittle");
    }

    @Override
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Spittle> findRecent(int count) {
        return jdbcTemplate.query(SELECT_RECENT_SPITTLES,
                new SpittleRowMapper(), count);
    }

    @Override
    public Spittle findOne(long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_SPITTLE_BY_ID,
                    new SpittleRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Spittle save(Spittle spittle) {
        long spittleId = insertSpittleAndReturnId(spittle);
        return new Spittle(spittleId, spittle.getSpitter(), spittle.getMessage(), spittle.getPostedTime());
    }

    @Override
    public List<Spittle> findBySpitterId(long spitterId) {
        return jdbcTemplate.query(SELECT_SPITTLES_BY_SPITTER_ID,
                new SpittleRowMapper(), spitterId);
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM Spittle WHERE id=?", id);
    }

    private long insertSpittleAndReturnId(Spittle spittle) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spittle");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<>();
        args.put("spitter", spittle.getSpitter().getId());
        args.put("message", spittle.getMessage());
        args.put("postedTime", spittle.getPostedTime());
        long spittleId = jdbcInsert.executeAndReturnKey(args).longValue();

        return spittleId;
    }

    private static final class SpittleRowMapper implements RowMapper<Spittle> {
        @Override
        public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            Date postedTime = rs.getTimestamp("postedTime");
            long spittleId = rs.getLong("spitterId");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String fullName = rs.getString("fullname");
            String email = rs.getString("email");
            boolean updateByEmail = rs.getBoolean("updateByEmail");
            Spitter spitter = new Spitter(spittleId, username, password, fullName, email, updateByEmail);

            return new Spittle(id, spitter, message, postedTime);
        }
    }
}
