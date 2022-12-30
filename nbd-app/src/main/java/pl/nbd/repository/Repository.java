package pl.nbd.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface Repository<T> extends AutoCloseable {

    Optional<T> findById(UUID clientId);

    T add(T entity);

    void remove(T entity);

    T update(T entity);

    long size();

    List<T> findAll();

}