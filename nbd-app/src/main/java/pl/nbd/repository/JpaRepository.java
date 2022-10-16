package pl.nbd.repository;

import java.util.List;
import java.util.UUID;

public interface JpaRepository<T> {

    T findById(Long id);

    T add(T entity);

    void remove(T entity);

    T update(T entity);

    long size();

    List<T> findAll();

}
