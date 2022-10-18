package pl.nbd.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.nbd.model.Client;
import pl.nbd.model.Client_;
import pl.nbd.model.Order;
import pl.nbd.model.Order_;

import java.util.List;

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
    public List<Client> findClientByName(String name){
        TypedQuery<Client> query =
                entityManager.createNamedQuery("findAllClientByName", Client.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public List<Order> findClientOrders(Client client){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        cq.select(order);
        cq.where(cb.equal(order.get(Order_.CLIENT), client));

        TypedQuery<Order> q = entityManager.createQuery(cq);
        List<Order> orders = q.getResultList();

        if(orders.isEmpty()) {
            throw new EntityNotFoundException("Orders not found for client: " + client);
        }
        return orders;
    }

}
