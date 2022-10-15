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

public class ItemRepository extends JpaRepositoryImpl<Item>  {
    public ItemRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Item findById(UUID id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> item = cq.from(Item.class);

        cq.select(item);
        cq.where(cb.equal(item.get(Item_.UNIQUE_ID), id));

        TypedQuery<Item> q = entityManager.createQuery(cq);
        List<Item> items = q.getResultList();

        if(items.isEmpty()) {
            throw new EntityNotFoundException("Order not found for uniqueId: " + id);
        }
        return items.get(0);
    }

    @Override
    public long size() {
        return entityManager.createQuery("Select count(item) from Item item", Long.class).getSingleResult();
    }

    @Override
    public List<Item> findAll(){
        return entityManager.createQuery("Select item from Item item", Item.class).getResultList();
    }
}
