package com.pcz.spittr.db.jpa;

import com.pcz.spittr.db.SpitterRepository;
import com.pcz.spittr.db.domain.Spitter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author picongzhi
 */
@Repository
public class JpaSpitterRepository implements SpitterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public Spitter save(Spitter spitter) {
        entityManager.persist(spitter);
        return spitter;
    }

    @Override
    public Spitter findOne(long id) {
        return entityManager.find(Spitter.class, id);
    }

    @Override
    public Spitter findByUsername(String username) {
        return (Spitter) entityManager
                .createQuery("SELECT s FROM Spitter s WHERE s.username=?")
                .setParameter(1, username)
                .getSingleResult();
    }

    @Override
    public List<Spitter> findAll() {
        return (List<Spitter>) entityManager
                .createQuery("SELECT s FROM Spitter s")
                .getResultList();
    }
}
