import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.nbd.manager.ClientManager;
import pl.nbd.manager.ItemManager;
import pl.nbd.manager.OrderManager;
import pl.nbd.manager.impl.ClientManagerImpl;
import pl.nbd.manager.impl.ItemManagerImpl;
import pl.nbd.manager.impl.OrderManagerImpl;
import pl.nbd.model.domain.*;
import pl.nbd.repository.cassandra.ClientRepository;
import pl.nbd.repository.cassandra.ItemRepository;
import pl.nbd.repository.cassandra.OrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderRepositoryTest {

    public static ClientRepository clientRepository;
    public static ItemRepository itemRepository;
    public static OrderRepository orderRepository;

    @BeforeAll
    public static void init(){
        clientRepository = new ClientRepository();
        clientRepository.initSession();
        clientRepository.createTable();
        orderRepository = new OrderRepository();
        orderRepository.initSession();
        orderRepository.createTable();
        itemRepository = new ItemRepository();
        itemRepository.initSession();
        itemRepository.createTable();
    }

    @Test
    public void orderTest() throws Exception {
        Client client = Client.builder()
                .name("Szymon")
                .surname("Ziemecki")
                .accountBalance(300d)
                .address(new Address("test","test","test"))
                .suspened(false)
                .uniqueId(UUID.randomUUID())
                .build();
        clientRepository.add(client);
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 50, "PC","intel", "DLUGI opis", 10d, true,"laptop");
        itemRepository.add(item);

        ItemManager itemManager = new ItemManagerImpl(itemRepository);
        ClientManager clientManager = new ClientManagerImpl(clientRepository);
        OrderManager orderManager = new OrderManagerImpl(orderRepository, itemManager, clientManager);

        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);

        Order order = orderManager.createOrder(client, new Address("Adres", "Zamowienia", "nr1"), orderItems);

        assertEquals(orderRepository.findById(order.getUniqueId()).get(), order);
    }

    @Test
    public void orderUpdateTest() throws Exception {
        Client client = Client.builder()
                .name("Szymon")
                .surname("Ziemecki")
                .accountBalance(300d)
                .address(new Address("test","test","test"))
                .suspened(false)
                .uniqueId(UUID.randomUUID())
                .build();
        clientRepository.add(client);
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 50, "PC","intel", "DLUGI opis", 10d, true,"laptop");
        itemRepository.add(item);

        ItemManager itemManager = new ItemManagerImpl(itemRepository);
        ClientManager clientManager = new ClientManagerImpl(clientRepository);
        OrderManager orderManager = new OrderManagerImpl(orderRepository, itemManager, clientManager);

        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);

        Order order = orderManager.createOrder(client, new Address("Adres", "Zamowienia", "nr1"), orderItems);

        assertEquals(orderRepository.findById(order.getUniqueId()).get(), order);
        assertEquals(orderRepository.findById(order.getUniqueId()).get().isDelivered(), false);
        order.setDelivered(true);
        orderRepository.update(order);
        assertEquals(orderRepository.findById(order.getUniqueId()).get(), order);
        assertEquals(orderRepository.findById(order.getUniqueId()).get().isDelivered(), true);
    }

    @Test
    public void orderDeleteTest() throws Exception {
        Client client = Client.builder()
                .name("Szymon")
                .surname("Ziemecki")
                .accountBalance(300d)
                .address(new Address("test","test","test"))
                .suspened(false)
                .uniqueId(UUID.randomUUID())
                .build();
        clientRepository.add(client);
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 50, "PC","intel", "DLUGI opis", 10d, true,"laptop");
        itemRepository.add(item);

        ItemManager itemManager = new ItemManagerImpl(itemRepository);
        ClientManager clientManager = new ClientManagerImpl(clientRepository);
        OrderManager orderManager = new OrderManagerImpl(orderRepository, itemManager, clientManager);

        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);

        Order order = orderManager.createOrder(client, new Address("Adres", "Zamowienia", "nr1"), orderItems);

        assertEquals(orderRepository.findById(order.getUniqueId()).get(), order);
        assertEquals(orderRepository.findById(order.getUniqueId()).get().isDelivered(), false);
        order.setDelivered(true);
        int ordersSize = orderRepository.findAll().size();
        orderRepository.remove(order);
        int sizeAfterDelete = orderRepository.findAll().size();
        assertEquals(ordersSize - 1, sizeAfterDelete);
    }

    @Test
    public void orderManagerNegativeTest() throws Exception {
        Client client = Client.builder()
                .name("Szymon")
                .surname("Ziemecki")
                .accountBalance(300d)
                .address(new Address("test","test","test"))
                .suspened(true)
                .uniqueId(UUID.randomUUID())
                .build();
        clientRepository.add(client);
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 50, "PC","intel", "DLUGI opis", 10d, true,"laptop");
        itemRepository.add(item);

        ItemManager itemManager = new ItemManagerImpl(itemRepository);
        ClientManager clientManager = new ClientManagerImpl(clientRepository);
        OrderManager orderManager = new OrderManagerImpl(orderRepository, itemManager, clientManager);

        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);

        assertThrows(Exception.class, () -> orderManager.createOrder(client, new Address("Adres", "Zamowienia", "nr1"), orderItems));
    }

    @Test
    public void orderManagerNegativeTest2() throws Exception {
        Client client = Client.builder()
                .name("Szymon")
                .surname("Ziemecki")
                .accountBalance(1d)
                .address(new Address("test","test","test"))
                .suspened(false)
                .uniqueId(UUID.randomUUID())
                .build();
        clientRepository.add(client);
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 50, "PC","intel", "DLUGI opis", 10d, true,"laptop");
        itemRepository.add(item);

        ItemManager itemManager = new ItemManagerImpl(itemRepository);
        ClientManager clientManager = new ClientManagerImpl(clientRepository);
        OrderManager orderManager = new OrderManagerImpl(orderRepository, itemManager, clientManager);

        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);

        assertThrows(Exception.class, () -> orderManager.createOrder(client, new Address("Adres", "Zamowienia", "nr1"), orderItems));
    }

    @SneakyThrows
    @AfterAll
    public static void ater(){
        itemRepository.close();
    }

}
