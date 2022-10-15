

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.model.*;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ClientRepository;
import pl.nbd.repository.OrderRepository;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ormTest {

    private static EntityManagerFactory emf;
    private static final Logger log = LoggerFactory.getLogger(ormTest.class);

    @BeforeAll
    static void beforeAll(){
        emf = Persistence.createEntityManagerFactory("POSTGRES");
    }

    @Test
    void testConnection() {
        AddressRepository addressRepository = new AddressRepository(emf.createEntityManager());
        ClientRepository clientRepository = new ClientRepository(emf.createEntityManager());
        OrderRepository orderRepository = new OrderRepository(emf.createEntityManager());
        Address address = new Address("Lodz", "Wroclawska", "3");
/**/        address.setUniqueId(UUID.randomUUID());
        Client client = new Client("Szymonn", "Ziemecki", address, Money.of(12,"PLN"));
/*        client.setUniqueId(UUID.randomUUID());*/
        clientRepository.add(client);

        UUID uuid = client.getUniqueId();
        Client client2 = clientRepository.findById(uuid);
        assertEquals(client, client2);

        client.setName("newName");
        clientRepository.update(client);

        Address address2 = new Address("Lod3z", "Wroclawska", "3");
        Client client3 = new Client("Szymonn", "Ziemecki", address2, Money.of(12,"PLN"));
        List<Item> items = new ArrayList<>();
        items.add(new Laptop("cpu", 20,20));
        items.add(new Laptop("cpu2", 10,10));
        clientRepository.add(client3);
        Order order = new Order(client3, address2, items, Money.of(12,"PLN"), false);
        orderRepository.add(order);

    }

    @Test
    void optimisticLockTest() {
        emf = Persistence.createEntityManagerFactory("POSTGRES");
        AddressRepository addressRepository = new AddressRepository(emf.createEntityManager());
        ClientRepository clientRepository = new ClientRepository(emf.createEntityManager());
        Address address = new Address("Lodz", "Wroclawska", "3");
        Client client = new Client("Szymonn", "Ziemecki", address, Money.of(12,"PLN"));
        UUID uuid = client.getUniqueId();
        clientRepository.add(client);
        uuid = client.getUniqueId();
        Client client2 = clientRepository.findById(uuid);
        assertEquals(client.getVersion(), 0);

        client.setSurname("Nowe");
        clientRepository.update(client);
        client.setVersion(0);
        client.setSurname("Nie zmieni");
        client.getAddress().setCity("zmiana");
        clientRepository.update(client);
    }


    @AfterAll
    static void afterAll() {
        if(emf != null){
            emf.close();
        }
    }
}
