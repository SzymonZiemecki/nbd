package pl.nbd.manager;

import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Order;

import java.util.Map;
import java.util.UUID;

public interface OrderManager {
    Order createOrder(Client client, Address orderAddress, Map<Long, Item> items) throws Exception;

    String report(UUID orderId);

    void deliverOrder(UUID id);
}
