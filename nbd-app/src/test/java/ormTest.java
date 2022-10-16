import jakarta.persistence.*;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.EntityFactory.EntityFactory;
import pl.nbd.model.*;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ClientRepository;
import pl.nbd.repository.ItemRepository;
import pl.nbd.repository.OrderRepository;


import javax.money.MonetaryAmount;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ormTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static final Logger log = LoggerFactory.getLogger(ormTest.class);

    private AddressRepository addressRepository;
    private ClientRepository clientRepository;
    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    @BeforeEach
    void beofreEach(){
        if(emf != null){
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory("POSTGRES");
        em = emf.createEntityManager();
        this.addressRepository = new AddressRepository(em);
        this.clientRepository = new ClientRepository(em);
        this.orderRepository = new OrderRepository(em);
        this.itemRepository = new ItemRepository(em);
    }

    @Test
    void testConnection() {

    }

    @Test
    void optimisticLockTest() {
        Address address = new Address("Lodz", "Wroclawska", "3");
        Client client = new Client("Szymonn", "Ziemecki", address, Money.of(12,"PLN"));
        clientRepository.add(client);
        Long id = client.getId();

        Client found1 = clientRepository.findById(id);
        Client found2 = emf.createEntityManager().find(Client.class, id);

        found1.setName("zmiana");
        clientRepository.update(found1);

        assertThrows(OptimisticLockException.class, () -> {
                found2.setName("fail");
                clientRepository.update(found2);});
    }

    @Test
    void ClientRepositoryTest(){
        Client client = EntityFactory.getClient();
        Long clientId = client.getId();
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
        assertEquals(clientRepository.findById(clientId), null);

        Client client2 = EntityFactory.getClient();
        Client client3 = EntityFactory.getClient();
        client3.setAddress(client2.getAddress());
        clientRepository.add(client2);
        clientRepository.add(client3);
    }

    @Test
    void OrderRepositoryTest(){
        Order order = EntityFactory.getOrder();
        Long orderId = order.getId();
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
        assertEquals(orderRepository.findById(orderId), null);
    }

    @Test
    void ItemRepositoryTest(){
        Item item = EntityFactory.getLaptop();
        Item item2 = EntityFactory.getLaptop();
        Long itemId = item.getId();
        itemRepository.add(item);

        assertEquals(itemRepository.size(), 1);
        assertEquals(item.getVersion(), 0);
        itemRepository.add(item2);
        assertEquals(itemRepository.findAll().size(), 2);
    }

    @Test
    public void initDatabase(){
        List<Address> addresses = Arrays.asList(EntityFactory.getAddres(),EntityFactory.getAddres(),EntityFactory.getAddres());
        List<Client> clients = Arrays.asList(EntityFactory.getClient(),EntityFactory.getClient(),EntityFactory.getClient());
        List<Item> items = Arrays.asList(EntityFactory.getLaptop(),EntityFactory.getLaptop(),EntityFactory.getLaptop());
        List<Order> orders = Arrays.asList(EntityFactory.getOrder(),EntityFactory.getOrder(),EntityFactory.getOrder());

        addresses.forEach(address -> this.addressRepository.add(address));
        clients.forEach(client -> this.clientRepository.add(client));
        items.forEach(item -> this.itemRepository.add(item));
        orders.forEach(order -> this.orderRepository.add(order));
    }
}
