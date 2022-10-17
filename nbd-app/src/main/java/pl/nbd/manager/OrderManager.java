package pl.nbd.manager;

import org.javamoney.moneta.Money;
import pl.nbd.model.Address;
import pl.nbd.model.Client;
import pl.nbd.model.Item;
import pl.nbd.model.Order;

import java.util.Map;

public interface OrderManager {
    public Order createOrder(Client client, Address orderAddress, Map<Long, Item> items) throws Exception;
}
