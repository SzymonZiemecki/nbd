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
    public Client findById(Long id){
        return entityManager.find(Client.class, id);
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
