package pl.nbd.manager.impl;

import org.javamoney.moneta.Money;
import pl.nbd.manager.OrderManager;
import pl.nbd.model.Address;
import pl.nbd.model.Client;
import pl.nbd.model.Item;
import pl.nbd.model.Order;
import pl.nbd.repository.ClientRepository;
import pl.nbd.repository.ItemRepository;
import pl.nbd.repository.OrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class OrderManagerImpl implements OrderManager {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private ClientRepository clientRepository;

    public OrderManagerImpl(OrderRepository orderRepository, ItemRepository itemRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Order createOrder(Client client, Address orderAddress, Map<Long, Item> items) throws Exception {
        Client contextClient = clientRepository.findById(client.getId());
        Money clientAccountBalance = client.getAccountBalance();
        Money orderValue = calculateOrderValue(items);

        if (isEnoughItems(items)
            && clientAccountBalance.subtract(orderValue).isGreaterThanOrEqualTo(Money.of(0, "PLN"))
            && !client.isSuspened()) {
            Map<Long, Item> processedItems = processItems(items);
            processedItems.values().forEach(item -> itemRepository.update(item));
            contextClient.setAccountBalance(contextClient.getAccountBalance().subtract(orderValue));
            clientRepository.update(contextClient);
            return orderRepository.add(new Order(client, client.getAddress(),processedItems,orderValue,true,false));

        } else {
            throw new Exception("failed to create order, violated business logic");
        }
    }

    private Money calculateOrderValue(Map<Long, Item> items) {
        AtomicReference<Money> orderValue = new AtomicReference<>(Money.of(0, "PLN"));
        items.forEach((key, value) -> {
            orderValue.set(orderValue.get().add(value.getPrice().multiply(key)));
        });
        return orderValue.get();
    }

    private boolean isEnoughItems(Map<Long, Item> items) {
        AtomicBoolean flag = new AtomicBoolean(true);
        items.forEach((key, value) -> {
            if (value.getAvailableAmount() - key < 0)
                flag.set(false);
        });
        return flag.get();
    }

    private Map<Long, Item> processItems(Map<Long, Item> items) {
        Map<Long, Item> updatedItems = new HashMap<>();
        items.forEach((key, value) -> {
            value.setAvailableAmount(value.getAvailableAmount() - key);
            updatedItems.put(key, value);
        });
        return updatedItems;
    }
}
