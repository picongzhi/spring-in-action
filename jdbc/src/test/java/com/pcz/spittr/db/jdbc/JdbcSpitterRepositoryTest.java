package com.pcz.spittr.db.jdbc;

import com.pcz.spittr.db.domain.Spitter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class JdbcSpitterRepositoryTest {
    @Autowired
    JdbcSpitterRepository spitterRepository;

    private static Spitter[] SPITTERS = new Spitter[6];

    @BeforeClass
    public static void before() {
        SPITTERS[0] = new Spitter(1L, "habuma", "password", "Craig Walls",
                "craig@habuma.com", false);
        SPITTERS[1] = new Spitter(2L, "mwalls", "password", "Michael Walls",
                "mwalls@habuma.com", true);
        SPITTERS[2] = new Spitter(3L, "chuck", "password", "Chuck Wagon",
                "chuck@habuma.com", false);
        SPITTERS[3] = new Spitter(4L, "artnames", "password", "Art Names",
                "art@habuma.com", true);
        SPITTERS[4] = new Spitter(5L, "newbee", "letmein", "New Bee",
                "newbee@habuma.com", true);
        SPITTERS[5] = new Spitter(4L, "arthur", "letmein", "Arthur Names",
                "arthur@habuma.com", false);
    }

    @Test
    public void count() {
        Assert.assertEquals(4, spitterRepository.count());
    }

    @Test
    @Transactional
    public void findAll() {
        List<Spitter> spitters = spitterRepository.findAll();
        Assert.assertEquals(4, spitters.size());
        assertSpitter(0, spitters.get(0));
        assertSpitter(1, spitters.get(1));
        assertSpitter(2, spitters.get(2));
        assertSpitter(3, spitters.get(3));
    }

    @Test
    @Transactional
    public void findByUsername() {
        assertSpitter(0, spitterRepository.findByUsername("habuma"));
        assertSpitter(1, spitterRepository.findByUsername("mwalls"));
        assertSpitter(2, spitterRepository.findByUsername("chuck"));
        assertSpitter(3, spitterRepository.findByUsername("artnames"));
    }

    @Test
    @Transactional
    public void findOne() {
        assertSpitter(0, spitterRepository.findOne(1L));
        assertSpitter(1, spitterRepository.findOne(2L));
        assertSpitter(2, spitterRepository.findOne(3L));
        assertSpitter(3, spitterRepository.findOne(4L));
    }

    @Test
    @Transactional
    public void saveNewSpitter() {
        Assert.assertEquals(4, spitterRepository.count());
        Spitter spitter = new Spitter(null, "newbee", "letmein",
                "New Bee", "newbee@habuma.com", true);
        Spitter saved = spitterRepository.save(spitter);
        Assert.assertEquals(5, spitterRepository.count());
        assertSpitter(4, saved);
        assertSpitter(4, spitterRepository.findOne(5L));
    }

    @Test
    @Transactional
    public void saveExistingSpitter() {
        Assert.assertEquals(4, spitterRepository.count());
        Spitter spitter = new Spitter(4L, "arthur", "letmein", "Arthur Names",
                "arthur@habuma.com", false);
        Spitter saved = spitterRepository.save(spitter);
        assertSpitter(5, saved);
        Assert.assertEquals(4, spitterRepository.count());
        Spitter updated = spitterRepository.findOne(4L);
        assertSpitter(5, updated);
    }

    private static void assertSpitter(int expectedSpitterIndex, Spitter actual) {
        assertSpitter(expectedSpitterIndex, actual, "Newbie");
    }

    private static void assertSpitter(int expectedSpitterIndex, Spitter actual, String expectedStatus) {
        Spitter expected = SPITTERS[expectedSpitterIndex];
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getFullName(), actual.getFullName());
        Assert.assertEquals(expected.getEmail(), actual.getEmail());
        Assert.assertEquals(expected.isUpdateByEmail(), actual.isUpdateByEmail());
    }
}
