package pl.nbd.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.nbd.model.Address;
import pl.nbd.model.Address_;
import pl.nbd.model.Client;

import java.util.List;
import java.util.UUID;

public class AddressRepository extends JpaRepositoryImpl<Address> {
    public AddressRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Address findById(Long id){
        return entityManager.find(Address.class, id);
    }

    @Override
    public long size() {
        return entityManager.createQuery("Select count(address) from Address address", Long.class).getSingleResult();
    }

    @Override
    public List<Address> findAll(){
       return entityManager.createQuery("Select address from Address address", Address.class).getResultList();
    }
}
