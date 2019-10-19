package com.pcz.profiles.profiles;

import static org.junit.Assert.*;

import com.pcz.profiles.DataSourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DataSourceConfigTest {
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = DataSourceConfig.class)
    @ActiveProfiles("dev")
    public static class DevDataSourceTest {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDataSource() {
            assertNotNull(dataSource);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<String> results = jdbcTemplate.query("SELECT id, name FROM Things", new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong("id") + ":" + rs.getString("name");
                }
            });

            assertEquals(1, results.size());
            assertEquals("1:A", results.get(0));
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes = DataSourceConfig.class)
    @ActiveProfiles("prod")
    public static class ProductionDataSourceTest {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDataSource() {
            assertNull(dataSource);
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath:datasource-config.xml")
    @ActiveProfiles("dev")
    public static class DevDataSourceXMLConfigTest {
        @Autowired
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDataSource() {
            assertNotNull(dataSource);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            List<String> results = jdbcTemplate.query("SELECT id, name FROM Things", new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return rs.getLong("id") + ":" + rs.getString("name");
                }
            });

            assertEquals(1, results.size());
            assertEquals("1:A", results.get(0));
        }
    }

    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration("classpath:datasource-config.xml")
    @ActiveProfiles("prod")
    public static class ProductionDataSourceXMLConfigTest {
        @Autowired(required = false)
        private DataSource dataSource;

        @Test
        public void shouldBeEmbeddedDataSource() {
            assertNull(dataSource);
        }
    }
}
