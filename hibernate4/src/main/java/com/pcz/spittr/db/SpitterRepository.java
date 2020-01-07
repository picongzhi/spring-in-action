package com.pcz.spittr.db;

import com.pcz.spittr.db.domain.Spitter;

import java.util.List;

/**
 * @author picongzhi
 */
public interface SpitterRepository {
    long count();

    Spitter save(Spitter spitter);

    Spitter findOne(long id);

    Spitter findByUsername(String username);

    List<Spitter> findAll();
}
