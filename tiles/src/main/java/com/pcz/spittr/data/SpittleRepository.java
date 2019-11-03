package com.pcz.spittr.data;

import com.pcz.spittr.Spittle;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpittleRepository {
    List<Spittle> findRecentSpittles();

    List<Spittle> findSpittles(long max, int count);

    Spittle findOne(long id);

    void save(Spittle spittle);
}
