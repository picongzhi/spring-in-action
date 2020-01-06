package com.pcz.spittr.db.jpa;

import com.pcz.spittr.db.SpittleRepository;
import com.pcz.spittr.domain.Spitter;
import com.pcz.spittr.domain.Spittle;
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
@ContextConfiguration(classes = JpaConfig.class)
public class SpittleRepositoryTest {
    @Autowired
    private SpittleRepository spittleRepository;

    @Test
    @Transactional
    public void count() {
        Assert.assertEquals(15, spittleRepository.count());
    }

    @Test
    @Transactional
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
    @Transactional
    public void findOne() {
        Spittle thriteen = spittleRepository.findOne(13L);
        Assert.assertEquals(13, thriteen.getId().longValue());
        Assert.assertEquals("Bonjour from Art!", thriteen.getMessage());
        Assert.assertEquals(1332682500000L, thriteen.getPostedTime().getTime());
        Assert.assertEquals(4, thriteen.getSpitter().getId().longValue());
        Assert.assertEquals("artnames", thriteen.getSpitter().getUsername());
        Assert.assertEquals("password", thriteen.getSpitter().getPassword());
        Assert.assertEquals("Art Names", thriteen.getSpitter().getFullName());
        Assert.assertEquals("art@habuma.com", thriteen.getSpitter().getEmail());
        Assert.assertTrue(thriteen.getSpitter().isUpdateByEmail());
    }

    @Test
    @Transactional
    public void findBySpitter() {
        List<Spittle> spittles = spittleRepository.findBySpitterId(4L);
        Assert.assertEquals(11, spittles.size());
        for (int i = 0; i < 11; i++) {
            Assert.assertEquals(i + 5, spittles.get(i).getId().longValue());
        }
    }

    @Test
    @Transactional
    public void save() {
        Assert.assertEquals(15, spittleRepository.count());
        Spitter spitter = spittleRepository.findOne(13L).getSpitter();
        Spittle spittle = new Spittle(null, spitter, "Un Nuevo Spittle from Art", new Date());
        Spittle saved = spittleRepository.save(spittle);
        Assert.assertEquals(16, spittleRepository.count());
        assertNewSpittle(saved);
        assertNewSpittle(spittleRepository.findOne(16L));
    }

    @Test
    @Transactional
    public void delete() {
        Assert.assertEquals(15, spittleRepository.count());
        Assert.assertNotNull(spittleRepository.findOne(13L));
        spittleRepository.delete(13L);
        Assert.assertEquals(14, spittleRepository.count());
        Assert.assertNull(spittleRepository.findOne(13L));
    }

    private void assertRecent(List<Spittle> recent, int count) {
        long[] recentIds = new long[]{3, 2, 1, 15, 14, 13, 12, 11, 10, 9};
        Assert.assertEquals(count, recent.size());
        for (int i = 0; i < count; i++) {
            Assert.assertEquals(recentIds[i], recent.get(i).getId().longValue());
        }
    }

    private void assertNewSpittle(Spittle spittle) {
        Assert.assertEquals(16, spittle.getId().longValue());
    }
}
