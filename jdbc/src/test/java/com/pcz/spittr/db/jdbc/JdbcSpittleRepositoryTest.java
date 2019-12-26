package com.pcz.spittr.db.jdbc;

import com.pcz.spittr.db.domain.Spitter;
import com.pcz.spittr.db.domain.Spittle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JdbcConfig.class)
public class JdbcSpittleRepositoryTest {
    @Autowired
    JdbcSpittleRepository spittleRepository;

    @Test
    public void count() {
        Assert.assertEquals(15, spittleRepository.count());
    }

    @Test
    public void findRecent() {
        {
            List<Spittle> recent = spittleRepository.findRecent();
            assertRecent(recent, 10);
        }

        {
            List<Spittle> recent = spittleRepository.findRecent(5);
            assertRecent(recent, 5);
        }
    }

    @Test
    public void findOne() {
        Spittle thirteen = spittleRepository.findOne(13);
        Assert.assertEquals(13, thirteen.getId().longValue());
        Assert.assertEquals("Bonjour from Art!", thirteen.getMessage());
        Assert.assertEquals(1332682500000L, thirteen.getPostedTime().getTime());
        Assert.assertEquals(4, thirteen.getSpitter().getId().longValue());
        Assert.assertEquals("artnames", thirteen.getSpitter().getUsername());
        Assert.assertEquals("password", thirteen.getSpitter().getPassword());
        Assert.assertEquals("Art Names", thirteen.getSpitter().getFullName());
        Assert.assertEquals("art@habuma.com", thirteen.getSpitter().getEmail());
        Assert.assertTrue(thirteen.getSpitter().isUpdateByEmail());
    }

    @Test
    public void findBySpitter() {
        List<Spittle> spittles = spittleRepository.findBySpitterId(4L);
        Assert.assertEquals(11, spittles.size());
        for (int i = 0; i < 11; i++) {
            Assert.assertEquals(15 - i, spittles.get(i).getId().longValue());
        }
    }

    @Test
    @Transactional
    public void save() {
        Assert.assertEquals(15, spittleRepository.count());
        Spitter spitter = spittleRepository.findOne(13).getSpitter();
        Spittle spittle = new Spittle(null, spitter, "Un Nuevo Spittle from Art",
                new Date());
        Spittle saved = spittleRepository.save(spittle);
        Assert.assertEquals(16, spittleRepository.count());
        assertNewSpittle(saved);
        assertNewSpittle(spittleRepository.findOne(16L));
    }

    @Test
    @Transactional
    public void delete() {
        Assert.assertEquals(15, spittleRepository.count());
        Assert.assertNotNull(spittleRepository.findOne(13));
        spittleRepository.delete(13L);
        Assert.assertEquals(14, spittleRepository.count());
        Assert.assertNull(spittleRepository.findOne(13));
    }

    private void assertRecent(List<Spittle> recent, int count) {
        long[] recentIds = new long[] {3, 2, 1, 15, 14, 13, 12, 11, 10, 9};
        Assert.assertEquals(count, recent.size());
        for (int i = 0; i < count; i++) {
            Assert.assertEquals(recentIds[i], recent.get(i).getId().longValue());
        }
    }

    private void assertNewSpittle(Spittle spittle) {
        Assert.assertEquals(16, spittle.getId().longValue());
    }
}
