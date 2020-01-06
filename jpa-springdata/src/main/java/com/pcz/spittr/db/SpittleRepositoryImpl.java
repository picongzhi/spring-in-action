package com.pcz.spittr.db;

import com.pcz.spittr.domain.Spittle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author picongzhi
 */
public class SpittleRepositoryImpl implements SpittleRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Spittle> findRecent(int count) {
        return entityManager.createQuery("SELECT s FROM Spittle s ORDER BY s.postedTime DESC")
                .setMaxResults(count)
                .getResultList();
    }
}
