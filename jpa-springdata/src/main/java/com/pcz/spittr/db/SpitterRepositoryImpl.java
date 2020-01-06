package com.pcz.spittr.db;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author picongzhi
 */
public class SpitterRepositoryImpl implements SpitterSweeper {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int eliteSweep() {
        return entityManager
                .createQuery("UPDATE Spitter spitter " +
                        "SET spitter.status = 'Elite' " +
                        "WHERE spitter.status = 'Newbie' " +
                        "AND spitter.id IN (SELECT s FROM Spitter s WHERE (SELECT COUNT(spittles) FROM s.spittles spittles) > 10000)")
                .executeUpdate();
    }
}
