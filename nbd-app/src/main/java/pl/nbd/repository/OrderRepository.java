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
    public Order findById(Long id){
        return entityManager.find(Order.class, id);
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
