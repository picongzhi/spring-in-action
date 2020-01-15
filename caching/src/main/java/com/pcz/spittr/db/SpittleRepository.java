package com.pcz.spittr.db;

import com.pcz.spittr.domain.Spittle;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpittleRepository {
    long count();

    @Cacheable("spittleCache")
    List<Spittle> findRecent();

    List<Spittle> findRecent(int count);

    @Cacheable("spittleCache")
    Spittle findOne(long id);

    @CachePut(value = "spittleCache", key = "#result.id")
    Spittle save(Spittle spittle);

    @Cacheable("spittleCache")
    List<Spittle> findBySpitterId(long spitterId);

    @CacheEvict(value = "spittleCache", condition = "")
    void delete(long id);
}
