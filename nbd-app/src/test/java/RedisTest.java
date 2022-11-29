import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.*;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mapper.ItemMapper;
import pl.nbd.model.mapper.OrderMapper;
import pl.nbd.repository.redis.ClientRedisRepository;
import pl.nbd.repository.redis.ItemRedisRepository;
import pl.nbd.repository.redis.OrderRedisRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RedisTest {
    ClientRedisRepository clients;
    OrderRedisRepository orders;
    ItemRedisRepository items;

    @BeforeEach
    public void before(){
        clients = new ClientRedisRepository();
        orders = new OrderRedisRepository();
        items = new ItemRedisRepository();
        clients.clearCache();
    }

    @Test
    public void addTest(){
        Client client = new Client(UUID.randomUUID(),"name","surname",new Address(),20.0,false);
        Client client2 = new Client(UUID.randomUUID(),"name2","surname3",new Address(),30.0,true);

        clients.add(ClientMapper.toRedisDocument(client));
        clients.add(ClientMapper.toRedisDocument(client2));

        assertEquals(clients.size(), 2);
    }

    @Test
    public void removeTest(){
        Client client = new Client(UUID.randomUUID(),"name","surname",new Address(),20.0,false);
        Client client2 = new Client(UUID.randomUUID(),"name2","surname3",new Address(),30.0,true);

        clients.add(ClientMapper.toRedisDocument(client));
        clients.add(ClientMapper.toRedisDocument(client2));

        clients.remove(ClientMapper.toRedisDocument(client));
        assertEquals(clients.size(), 1);
    }

    @Test
    public void clearAllCacheTest() {
        Client client = new Client(UUID.randomUUID(),"name","surname",new Address(),20.0,false);
        Client client2 = new Client(UUID.randomUUID(),"name2","surname3",new Address(),30.0,true);
        Item item = new Laptop(2, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);

        Map<String, Item> orderItems = new HashMap<>();
        orderItems.put("2", item);
        Order order = new Order(UUID.randomUUID(), client, new Address(), orderItems, 200.0, new Date(), false, false);

        orders.add(OrderMapper.toRedisDocument(order));
        items.add(ItemMapper.toRedisDocument(item));
        clients.add(ClientMapper.toRedisDocument(client));
        clients.add(ClientMapper.toRedisDocument(client2));

        clients.clearCache();
        assertEquals(clients.size(), 0);
        assertEquals(orders.size(), 0);
        assertEquals(items.size(), 0);

    }

    @Test
    public void clearSpecificCacheTest() {

        Client client = new Client(UUID.randomUUID(),"name","surname",new Address(),20.0,false);
        Client client2 = new Client(UUID.randomUUID(),"name2","surname3",new Address(),30.0,true);
        Item item = new Laptop(2, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);

        Map<String, Item> orderItems = new HashMap<>();
        orderItems.put("2", item);
        Order order = new Order(UUID.randomUUID(), client, new Address(), orderItems, 200.0, new Date(), false, false);

        orders.add(OrderMapper.toRedisDocument(order));
        items.add(ItemMapper.toRedisDocument(item));
        clients.add(ClientMapper.toRedisDocument(client));
        clients.add(ClientMapper.toRedisDocument(client2));

        clients.clearThis();
        assertEquals(clients.size(), 0);
        assertEquals(orders.size(), 1);
        assertEquals(items.size(), 1);
    }
}
