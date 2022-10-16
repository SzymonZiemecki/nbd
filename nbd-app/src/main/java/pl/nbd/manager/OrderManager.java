package pl.nbd.manager;

import jakarta.transaction.Transactional;
import org.javamoney.moneta.Money;
import pl.nbd.model.Address;
import pl.nbd.model.Client;
import pl.nbd.model.Item;
import pl.nbd.model.Order;
import pl.nbd.repository.ClientRepository;
import pl.nbd.repository.ItemRepository;
import pl.nbd.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderManager {

    private OrderRepository orderRepository;
    private ItemRepository itemRepository;
    private ClientRepository clientRepository;

    public OrderManager(OrderRepository orderRepository, ItemRepository itemRepository, ClientRepository clientRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Order createOrder(Client client, Address orderAddress, Map<Long, Item> items) throws Exception {
        Money clientAccountBalance = client.getAccountBalance();
        Money orderValue = Money.of(0, "PLN");
        List<Item> updatedItems = new ArrayList<>();

        items.forEach((key, value) -> {
            Item foundItem = itemRepository.findById(value.getId());
            foundItem.setAvailableAmount(foundItem.getAvailableAmount() - key);
            updatedItems.add(foundItem);
        });

        for (Item item : items.values()) {
            orderValue = orderValue.add(item.getPrice());
            if(item.getAvailableAmount() < 0){
                throw new Exception("failed to create order, violated business logic - items out of stock");
            }
        }

        clientAccountBalance = clientAccountBalance.subtract(orderValue);
        if (clientAccountBalance.isGreaterThanOrEqualTo(Money.of(0, "PLN"))) {
            updatedItems.forEach( item -> itemRepository.update(item));
            return orderRepository.add(new Order(client, orderAddress, items, orderValue, true));
        } else {
            throw new Exception("failed to create order, violated business logic - client doesnt have enough money");
        }
    }
}
