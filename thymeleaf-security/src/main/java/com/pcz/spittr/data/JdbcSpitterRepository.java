package com.pcz.spittr.data;

import com.pcz.spittr.Spitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * @author picongzhi
 */
@Repository
public class JdbcSpitterRepository implements SpitterRepository {
    private JdbcOperations jdbcOperations;

    @Autowired
    public JdbcSpitterRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Spitter save(Spitter spitter) {
        jdbcOperations.update("INSERT INTO Spitter (username, password, first_name, last_name, email) VALUES (?, ?, ?, ?, ?)",
                spitter.getUsername(), spitter.getPassword(), spitter.getFirstName(), spitter.getLastName(), spitter.getEmail());

        return spitter;
    }

    @Override
    public Spitter findByUsername(String username) {
        return jdbcOperations.queryForObject("SELECT id, username, null, first_name, last_name email from Spitter WHERE username = ?",
                (rs, rowNum) -> new Spitter(
                        rs.getLong("id"),
                        rs.getString("username"),
                        null,
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")), username);
    }
}
