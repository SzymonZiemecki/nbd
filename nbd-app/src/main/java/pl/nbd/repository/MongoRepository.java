package pl.nbd.repository;

import java.util.List;

public interface MongoRepository<T> extends AutoCloseable {

    T findById(Long id);

    T add(T entity);

    void remove(T entity);

    T update(T entity);

    long size();

    List<T> findAll();

}