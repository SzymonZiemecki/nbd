/*
package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.manager.ItemManager;
import pl.nbd.manager.OrderManager;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Order;
import pl.nbd.model.mapper.OrderMapper;
import pl.nbd.repository.mongo.OrderMgdRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class OrderManagerImpl implements OrderManager {

    private OrderMgdRepository orderRepository;
    private ClientManager clientManager;
    private ItemManager itemManager;

    public OrderManagerImpl(OrderMgdRepository orderRepository, ItemManager itemManager, ClientManager clientManager) {
        this.orderRepository = orderRepository;
        this.clientManager = clientManager;
        this.itemManager = itemManager;
    }

    @Override
    public Order createOrder(Client client, Address orderAddress, Map<Long, Item> items) throws Exception {
        Client contextClient = clientManager.findClientsById(client.getUniqueId()).get();
        Double clientAccountBalance = contextClient.getAccountBalance();
        Double orderValue = calculateOrderValue(items);

        if (isEnoughItems(items)
            && (clientAccountBalance - orderValue >= 0)
            && !contextClient.isSuspened()) {
            Map<String, Item> processedItems = processItems(items);
            processedItems.values().forEach(item -> itemManager.updateItem(item));
            contextClient.setAccountBalance(contextClient.getAccountBalance() - orderValue);
            clientManager.updateClient(contextClient);
            return OrderMapper.toDomainModel(orderRepository.add(OrderMapper.toMongoDocument(new Order(contextClient, contextClient.getAddress(),processedItems,orderValue,true,false))));

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
        Order order = OrderMapper.toDomainModel(orderRepository.findById(id).get());
        order.setDelivered(true);
        orderRepository.update(OrderMapper.toMongoDocument(order));
    }

    private Double calculateOrderValue(Map<Long, Item> items) {
        AtomicReference<Double> orderValue = new AtomicReference<>((double) 0);
        items.keySet().forEach(key -> {
            orderValue.updateAndGet(v -> new Double((double) (v + key * items.get(key).getPrice())));
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

    private Map<String, Item> processItems(Map<Long, Item> items) {
        Map<String, Item> updatedItems = new HashMap<>();
        items.forEach((key, value) -> {
            value.setAvailableAmount(value.getAvailableAmount() - key);
            if (value.getAvailableAmount() > 0) {
                value.setAvailable(true);
            } else {
                value.setAvailable(false);
            }
            updatedItems.put(String.valueOf(key), value);
        });
        return updatedItems;
    }
}
*/
