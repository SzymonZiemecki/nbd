package pl.nbd.repository;

public interface JpaRepository<T> {

    T persist(T entity);
    void flush();
    T update(T entity);
    void delele(T entity);

}
