package pl.nbd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.model.AbstractEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class JpaRepositoryImpl<T extends AbstractEntity> implements JpaRepository<T>{

    protected EntityManager entityManager;
    private static final Logger log = LoggerFactory.getLogger(JpaRepositoryImpl.class);

    public JpaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T findById(Long id) {
        throw new NotImplementedException();
    }

    @Override
    public T add(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(entity);
        entityTransaction.commit();
        return entity;
    }

    @Override
    public void remove(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.remove(entity);
        entityTransaction.commit();
    }

    @Override
    public T update(T entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.merge(entity);
        entityTransaction.commit();
        return entity;
    }

    @Override
    public long size() {
        throw new NotImplementedException();
    }

    @Override
    public List<T> findAll() {
        throw new NotImplementedException();
    }

}
