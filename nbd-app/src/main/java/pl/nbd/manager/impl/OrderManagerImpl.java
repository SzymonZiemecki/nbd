package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.manager.ItemManager;
import pl.nbd.manager.OrderManager;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Order;
import pl.nbd.repository.cassandra.OrderRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderManagerImpl implements OrderManager {

    private OrderRepository orderRepository;
    private ClientManager clientManager;
    private ItemManager itemManager;

    public OrderManagerImpl(OrderRepository orderRepository, ItemManager itemManager, ClientManager clientManager) {
        this.orderRepository = orderRepository;
        this.clientManager = clientManager;
        this.itemManager = itemManager;
    }

    @Override
    public Order createOrder(Client client, Address orderAddress, Map<UUID, Long> items) throws Exception {
        Client contextClient = clientManager.findClientById(client.getUniqueId());
        Double clientAccountBalance = contextClient.getAccountBalance();

        Map<Item,Long> resolvedItems = toItemMap(items);

        Double orderValue = calculateOrderValue(resolvedItems);

        if (isEnoughItems(resolvedItems)
                && (clientAccountBalance - orderValue >= 0)
                && !contextClient.isSuspened()) {
            Map<Item, Long> processedItems = processItems(resolvedItems);
            processedItems.keySet().forEach(item -> itemManager.updateItem(item));
            contextClient.setAccountBalance(contextClient.getAccountBalance() - orderValue);
            clientManager.updateClient(contextClient);
            return orderRepository.add(new Order(contextClient.getUniqueId(), contextClient.getAddress(),toUuidMap(processedItems),orderValue,true,false));

        } else {
            throw new Exception("failed to create order, violated business logic");
        }
    }
    @Override
    public String report(UUID orderId){
        return orderRepository.findById(orderId).toString();
    }

    @Override
    public void deliverOrder(UUID id) {
        Order order = orderRepository.findById(id).get();
        order.setDelivered(true);
        orderRepository.update(order);
    }

    private Double calculateOrderValue(Map<Item, Long> items) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        items.forEach((key,value) -> {
            orderValue.updateAndGet(v -> (double) (v + key.getPrice() * value));
        });
        return orderValue.get();
    }

    private boolean isEnoughItems(Map<Item, Long> items) {
        AtomicBoolean flag = new AtomicBoolean(true);
        items.forEach((key, value) -> {
            if (key.getAvailableAmount() - value < 0)
                flag.set(false);
        });
        return flag.get();
    }

    private Map<Item, Long> processItems(Map<Item, Long> items) {
        Map<Item, Long> updatedItems = new HashMap<>();
        items.forEach((key, value) -> {
            key.setAvailableAmount(key.getAvailableAmount() - value);
            if (key.getAvailableAmount() > 0) {
                key.setAvailable(true);
            } else {
                key.setAvailable(false);
            }
            updatedItems.put(key, value);
        });
        return updatedItems;
    }

    private Map<UUID, Long> toUuidMap(Map<Item, Long> items){
        return items.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getUniqueId(), Map.Entry::getValue));
    }

    private Map<Item, Long> toItemMap(Map<UUID, Long> items){
        return items.entrySet().stream()
                .collect(Collectors.toMap(entry -> itemManager.findItemById(entry.getKey()), Map.Entry::getValue));
    }
}