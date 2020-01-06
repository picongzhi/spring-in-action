package com.pcz.spittr.db;

import com.pcz.spittr.db.domain.Spittle;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpittleRepository {
    long count();

    List<Spittle> findRecent();

    List<Spittle> findRecent(int count);

    Spittle findOne(long id);

    Spittle save(Spittle spittle);

    List<Spittle> findBySpitterId(long spitterId);

    void delete(long id);

    List<Spittle> findAll();
}
