

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.From;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.model.Client;
import pl.nbd.repository.ClientRepository;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ormTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static Logger log = LoggerFactory.getLogger(ormTest.class);

    @BeforeAll
    static void beforeAll(){
        emf = Persistence.createEntityManagerFactory("POSTGRES_RENT_PU");
        em = emf.createEntityManager();
    }

    @Test
    void testConnection() {
        Client client = em.find(Client.class, UUID.randomUUID());
        assertEquals(client,null);
        ClientRepository clientRepository = new ClientRepository(em);
        Client client1 = new Client("siema", "bracie");
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        clientRepository.persist(client1);
        System.out.println("LOG");
        entityTransaction.commit();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        From<Client,Client> from = query.from(Client.class);
        query.select(cb.count(from));
        log.info(em.createQuery(query).getSingleResult().toString());
    }

    @AfterAll
    static void afterAll() {
        if(emf != null){
            em.close();
            emf.close();
        }
    }
}
