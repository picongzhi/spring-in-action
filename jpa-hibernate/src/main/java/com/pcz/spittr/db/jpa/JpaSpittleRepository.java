package com.pcz.spittr.db.jpa;

import com.pcz.spittr.db.SpittleRepository;
import com.pcz.spittr.db.domain.Spittle;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author picongzhi
 */
@Repository
public class JpaSpittleRepository implements SpittleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Spittle> findRecent(int count) {
        return (List<Spittle>) entityManager
                .createQuery("SELECT s FROM Spittle s ORDER BY s.postedTime DESC")
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public Spittle findOne(long id) {
        return entityManager.find(Spittle.class, id);
    }

    @Override
    public Spittle save(Spittle spittle) {
        entityManager.persist(spittle);
        return spittle;
    }

    @Override
    public List<Spittle> findBySpitterId(long spitterId) {
        return (List<Spittle>) entityManager
                .createQuery("SELECT s FROM Spittle s, Spitter sp WHERE s.spitter = sp and sp.id=? ORDER BY s.postedTime DESC")
                .setParameter(1, spitterId)
                .getResultList();
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findOne(id));
    }

    @Override
    public List<Spittle> findAll() {
        return (List<Spittle>) entityManager
                .createQuery("SELECT s FROM Spittle s")
                .getResultList();
    }
}
