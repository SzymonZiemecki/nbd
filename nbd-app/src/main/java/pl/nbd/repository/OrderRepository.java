package pl.nbd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.nbd.model.*;

import java.util.List;
import java.util.UUID;

public class OrderRepository extends JpaRepositoryImpl<Order> {
    public OrderRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Order findById(UUID id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        cq.select(order);
        cq.where(cb.equal(order.get(Order_.UNIQUE_ID), id));

        TypedQuery<Order> q = entityManager.createQuery(cq);
        List<Order> orders = q.getResultList();

        if(orders.isEmpty()) {
            throw new EntityNotFoundException("Address not found for uniqueId: " + id);
        }
        return orders.get(0);
    }

    @Override
    public long size() {
        return entityManager.createQuery("Select count(orders) from Order orders", Long.class).getSingleResult();
    }

    @Override
    public List<Order> findAll(){
        return entityManager.createQuery("Select orders from Order orders", Order.class).getResultList();
    }
}
