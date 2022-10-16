package pl.nbd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.model.AbstractEntity;

import java.util.List;
import java.util.UUID;

public abstract class JpaRepositoryImpl<T extends AbstractEntity> implements JpaRepository<T>{

    protected EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger(JpaRepositoryImpl.class);

    public JpaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public void add(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        log.info("test");
        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();
    }

    @Override
    public void remove(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(entity);
        entityTransaction.commit();
    }

    @Override
    public void update(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(entity);
        entityTransaction.commit();
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

}
