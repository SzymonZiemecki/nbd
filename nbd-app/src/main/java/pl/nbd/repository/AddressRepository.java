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
import pl.nbd.model.Client_;

import java.util.List;
import java.util.UUID;

public class AddressRepository extends JpaRepositoryImpl<Address> {
    public AddressRepository(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Address findById(UUID id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        Root<Address> address = cq.from(Address.class);

        cq.select(address);
        cq.where(cb.equal(address.get(Address_.UNIQUE_ID), id));

        TypedQuery<Address> q = entityManager.createQuery(cq);
        List<Address> addresses = q.getResultList();

        if(addresses.isEmpty()) {
            throw new EntityNotFoundException("Address not found for uniqueId: " + id);
        }
        return addresses.get(0);
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
