package pl.nbd.repository;

import java.util.List;
import java.util.UUID;

public interface JpaRepository<T> {

    T findById(UUID id);

    void add(T entity);

    void remove(T entity);

    void update(T entity);

    long size();

    List<T> findAll();

}
