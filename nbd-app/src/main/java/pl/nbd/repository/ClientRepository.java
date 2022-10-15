package pl.nbd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import jakarta.transaction.Transactional;
import pl.nbd.model.Client;

import javax.enterprise.inject.Produces;

@Transactional
public class ClientRepository implements JpaRepository<Client>{

    @Produces
    @PersistenceContext(unitName = "POSTGRES_RENT_PU", type = PersistenceContextType.EXTENDED)
    EntityManager entityManager;

    public ClientRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ClientRepository() {
    }

    @Override
    public Client persist(Client entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public void flush() {

    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public void delele(Client entity) {

    }
}
