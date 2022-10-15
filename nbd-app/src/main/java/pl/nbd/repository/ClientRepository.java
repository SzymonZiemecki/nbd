package pl.nbd.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.nbd.model.Address;
import pl.nbd.model.Client;
import pl.nbd.model.Client_;

import javax.enterprise.inject.Produces;
import java.util.List;
import java.util.UUID;

public class ClientRepository extends JpaRepositoryImpl<Client> {

    public ClientRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Client findById(UUID id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> client = cq.from(Client.class);

        cq.select(client);
        cq.where(cb.equal(client.get(Client_.UNIQUE_ID), id));

        TypedQuery<Client> q = entityManager.createQuery(cq);
        List<Client> clients = q.getResultList();

        if(clients.isEmpty()) {
            throw new EntityNotFoundException("Client not found for uniqueId: " + id);
        }
        return clients.get(0);
    }

    @Override
    public long size() {
        return entityManager.createQuery("Select count(client) from Client client", Long.class).getSingleResult();
    }

    @Override
    public List<Client> findAll(){
        List<Client> clients = entityManager.createQuery("Select client from Client client", Client.class).getResultList();
        return clients;
    }

}
