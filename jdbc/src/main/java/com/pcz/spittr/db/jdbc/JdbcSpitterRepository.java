package com.pcz.spittr.db.jdbc;

import com.pcz.spittr.db.SpitterRepository;
import com.pcz.spittr.db.domain.Spitter;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author picongzhi
 */
public class JdbcSpitterRepository implements SpitterRepository {
    private static final String INSERT_SPITTER = "INSERT INTO Spitter (username, password, fullname, email, updateByEmail) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_SPITTER = "SELECT id, username, password, fullname, email, updateByEmail FROM Spitter";

    private JdbcTemplate jdbcTemplate;

    public JdbcSpitterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForLong("SELECT COUNT(id) FROM Spitter");
    }

    @Override
    public Spitter save(Spitter spitter) {
        Long id = spitter.getId();
        if (id == null) {
            long spitterId = insertSpitterAndReturnId(spitter);
            return new Spitter(spitterId, spitter.getUsername(), spitter.getPassword(), spitter.getFullName(),
                    spitter.getEmail(), spitter.isUpdateByEmail());
        } else {
            jdbcTemplate.update("UPDATE Spitter SET username=?, password=?, fullname=?, email=?, updateByEmail=? WHERE id=?",
                    spitter.getUsername(),
                    spitter.getPassword(),
                    spitter.getFullName(),
                    spitter.getEmail(),
                    spitter.isUpdateByEmail(),
                    id);
        }

        return spitter;
    }

    private long insertSpitterAndReturnId(Spitter spitter) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Spitter");
        jdbcInsert.setGeneratedKeyName("id");
        Map<String, Object> args = new HashMap<>();
        args.put("username", spitter.getUsername());
        args.put("password", spitter.getPassword());
        args.put("fullname", spitter.getFullName());
        args.put("email", spitter.getEmail());
        args.put("updateByEmail", spitter.isUpdateByEmail());
        long spitterId = jdbcInsert.executeAndReturnKey(args).longValue();

        return spitterId;
    }

    @Override
    public Spitter findOne(long id) {
        return jdbcTemplate.queryForObject(SELECT_SPITTER + " WHERE id=?",
                new SpitterRowMapper(), id);
    }

    @Override
    public Spitter findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT id, username, password, fullname, email, updateByEmail FROM Spitter WHERE username=?",
                new SpitterRowMapper(), username);
    }

    @Override
    public List<Spitter> findAll() {
        return jdbcTemplate.query("SELECT id, username, password, fullname, email, updateByEmail FROM Spitter ORDER BY id",
                new SpitterRowMapper());
    }

    private static final class  SpitterRowMapper implements RowMapper<Spitter> {
        @Override
        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Spitter(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("fullname"),
                    rs.getString("email"),
                    rs.getBoolean("updateByEmail"));
        }
    }
}
