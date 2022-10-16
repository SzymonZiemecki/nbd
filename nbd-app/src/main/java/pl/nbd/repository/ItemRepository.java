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
    public Item findById(Long id){
        return entityManager.find(Item.class, id);
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
