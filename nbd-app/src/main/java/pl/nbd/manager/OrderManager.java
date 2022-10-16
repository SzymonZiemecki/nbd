package pl.nbd.manager;

import pl.nbd.repository.OrderRepository;

public class OrderManager {

    private OrderRepository orderRepository;
    public OrderManager(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
