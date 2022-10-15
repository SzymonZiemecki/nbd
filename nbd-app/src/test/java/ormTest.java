import jakarta.persistence.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.EntityFactory.EntityFactory;
import pl.nbd.model.*;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ClientRepository;
import pl.nbd.repository.OrderRepository;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        address.setUniqueId(UUID.randomUUID());
        Client client = new Client("Szymonn", "Ziemecki", address, Money.of(12,"PLN"));
        clientRepository.add(client);

        UUID uuid = client.getUniqueId();
        Client client2 = clientRepository.findById(uuid);
        assertEquals(client, client2);

        client.setName("newName");
        clientRepository.update(client);

        Address address2 = new Address("Lod3z", "Wroclawska", "3");
        Client client3 = new Client("Szymonn", "Ziemecki", address2, Money.of(12,"PLN"));
        List<Item> items = new ArrayList<>();
        items.add(new Laptop(450, "komputo","hp","desc", Money.of(12,"PLN"), "cpu", 12,12));
        items.add(new Laptop(450, "komputo2","hp","desc", Money.of(12,"PLN"), "cpu2", 12,12));
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
        clientRepository.add(client);
        Long id = client.getId();

        Client found1 = emf.createEntityManager().find(Client.class, id);
        Client found2 = emf.createEntityManager().find(Client.class, id);

        found1.setName("zmiana");
        clientRepository.update(found1);

        assertThrows(OptimisticLockException.class, () -> {
                found2.setName("fail");
                clientRepository.update(found2);});
    }

    @Test
    void ClientRepositoryTest(){
        ClientRepository clientRepository = new ClientRepository(emf.createEntityManager());
        Client client = EntityFactory.getClient();
        UUID clientUUID = client.getUniqueId();
        clientRepository.add(client);

        assertEquals(clientRepository.size(), 1);
        assertEquals(client.getVersion(), 0);

        client.setName("newName");
        clientRepository.update(client);
        assertEquals(clientRepository.size(), 1);
        assertEquals(client.getVersion(), 1);
        assertEquals(client.getName(), "newName");

        clientRepository.remove(client);
        assertEquals(clientRepository.size(), 0);
        assertThrows(EntityNotFoundException.class, () -> {
            clientRepository.findById(clientUUID);
        });
    }

    @Test
    void OrderRepositoryTest(){
        OrderRepository orderRepository = new OrderRepository(emf.createEntityManager());
        Order order = EntityFactory.getOrder();
        UUID orderUUID = order.getUniqueId();
        orderRepository.add(order);

        assertEquals(orderRepository.size(), 1);
        assertEquals(order.getVersion(), 0);

        order.setPaid(true);
        orderRepository.update(order);
        assertEquals(orderRepository.size(), 1);
        assertEquals(order.getVersion(), 1);
        assertEquals(order.isPaid(), true);

        orderRepository.remove(order);
        assertEquals(orderRepository.size(), 0);
        assertThrows(EntityNotFoundException.class, () -> {
            orderRepository.findById(orderUUID);
        });
    }

    @AfterAll
    static void afterAll() {
        if(emf != null){
            emf.close();
        }
    }
}
