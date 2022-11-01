import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.nbd.manager.ClientManager;
import pl.nbd.manager.ItemManager;
import pl.nbd.manager.OrderManager;
import pl.nbd.manager.impl.ClientManagerImpl;
import pl.nbd.manager.impl.ItemManagerImpl;
import pl.nbd.manager.impl.OrderManagerImpl;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mgd.AddressMgd;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.repository.mongo.ClientMgdRepository;
import pl.nbd.repository.mongo.OrderMgdRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderManagerTest {

    OrderManager orderManager;
    ItemManager itemManager;
    ClientManager clientManager;

    OrderMgdRepository orderMgdRepository;


    @BeforeEach
    public void before() {
        orderMgdRepository = new OrderMgdRepository();
        itemManager = new ItemManagerImpl();
        clientManager = new ClientManagerImpl();
        orderManager = new OrderManagerImpl(orderMgdRepository, itemManager, clientManager);

    }


    @Test
    public void createOrderPositiveTest() throws Exception {
        Item item = new Laptop(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        item = itemManager.addItem(item);
        Client client = new Client("test", "test", new Address("test", "test", "301"), 100.0, false);
        client = clientManager.addClient(client);
        Map<Long, Item> orderItems = new HashMap<>();
        orderItems.put(3l, itemManager.findItemById(item.getUniqueId()).get());
        orderManager.createOrder(clientManager.findClientsById(client.getUniqueId()).get(), clientManager.findClientsById(client.getUniqueId()).get().getAddress(), orderItems);
        assertEquals(orderMgdRepository.size(), 1);
    }

    @Test
    public void createOrderNegativeTest() throws Exception {
        Item item = new Laptop(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        item = itemManager.addItem(item);
        Client client = new Client("test", "test", new Address("test", "test", "301"), 100.0, true);
        client = clientManager.addClient(client);
        Map<Long, Item> orderItems = new HashMap<>();
        orderItems.put(3l, itemManager.findItemById(item.getUniqueId()).get());
        Client finalClient = client;
        Exception e = assertThrows(Exception.class, () -> {
            orderManager.createOrder(clientManager.findClientsById(finalClient.getUniqueId()).get(), clientManager.findClientsById(finalClient.getUniqueId()).get().getAddress(), orderItems);
        });
        assertEquals(e.getMessage(), "failed to create order, violated business logic");
    }

    @Test
    public void createOrderNegativeTest2() throws Exception {
        Item item = new Laptop(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        item = itemManager.addItem(item);
        Client client = new Client("test", "test", new Address("test", "test", "301"), 59.0, false);
        client = clientManager.addClient(client);
        Map<Long, Item> orderItems = new HashMap<>();
        orderItems.put(3l, itemManager.findItemById(item.getUniqueId()).get());
        Client finalClient = client;
        Exception e = assertThrows(Exception.class, () -> {
            orderManager.createOrder(clientManager.findClientsById(finalClient.getUniqueId()).get(), clientManager.findClientsById(finalClient.getUniqueId()).get().getAddress(), orderItems);
        });
        assertEquals(e.getMessage(), "failed to create order, violated business logic");
    }



    @AfterEach
    public void after() throws Exception {
        orderMgdRepository.close();
    }


}
