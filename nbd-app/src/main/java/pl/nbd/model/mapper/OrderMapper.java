package pl.nbd.model.mapper;

import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.domain.Order;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.OrderMgd;
import pl.nbd.model.redis.ItemJson;
import pl.nbd.model.redis.OrderJson;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderMapper {

    public static OrderMgd toMongoDocument(Order order){
        Map<String, ItemMgd> orderMgdItems = new HashMap<>();
        order.getOrderItems().keySet().forEach( key ->{
            orderMgdItems.put(key, ItemMapper.toMongoDocument(order.getOrderItems().get(key)));
                }
        );
        return OrderMgd.builder()
                .uniqueId(order.getUniqueId())
                .client(ClientMapper.toMongoDocument(order
                        .getClient()))
                .shippingAddress(AddressMapper.toMongoDocument(order.getShippingAddress()))
                .orderItems(orderMgdItems)
                .orderValue(order.getOrderValue())
                .createdOn(order.getCreatedOn())
                .isPaid(order.isPaid())
                .isDelivered(order.isDelivered())
                .build();
    }

    public static OrderJson toRedisDocument(Order order){
        Map<String, ItemJson> orderJsonItems = new HashMap<>();
        order.getOrderItems().keySet().forEach( key ->{
            orderJsonItems.put(key, ItemMapper.toRedisDocument(order.getOrderItems().get(key)));
                }
        );
        if (order.getUniqueId() == null) {
            order.setUniqueId(UUID.randomUUID());
        }
        return OrderJson.builder()
                .uniqueId(order.getUniqueId())
                .client(ClientMapper.toRedisDocument(order
                        .getClient()))
                .shippingAddress(AddressMapper.toRedisDocument(order.getShippingAddress()))
                .orderItems(orderJsonItems)
                .orderValue(order.getOrderValue())
                .createdOn(order.getCreatedOn())
                .isPaid(order.isPaid())
                .isDelivered(order.isDelivered())
                .build();
    }

    public static Order toDomainModel(OrderMgd order){
        Map<String, Item> orderItems = new HashMap<>();
        order.getOrderItems().keySet().forEach( key ->{
                    orderItems.put(key, ItemMapper.toDomainModel(order.getOrderItems().get(key)));
                }
        );
        return Order.builder()
                .uniqueId(order.getUniqueId())
                .client(ClientMapper.toDomainModel(order.getClient()))
                .shippingAddress(AddressMapper.toDomainModel(order.getShippingAddress()))
                .orderItems(orderItems)
                .orderValue(order.getOrderValue())
                .createdOn(order.getCreatedOn())
                .isPaid(order.isPaid())
                .isDelivered(order.isDelivered())
                .build();
    }

    public static Order toDomainModel(OrderJson order){
        Map<String, Item> orderItems = new HashMap<>();
        order.getOrderItems().keySet().forEach( key ->{
                    orderItems.put(key, ItemMapper.toDomainModel(order.getOrderItems().get(key)));
                }
        );
        return Order.builder()
                .uniqueId(order.getUniqueId())
                .client(ClientMapper.toDomainModel(order.getClient()))
                .shippingAddress(AddressMapper.toDomainModel(order.getShippingAddress()))
                .orderItems(orderItems)
                .orderValue(order.getOrderValue())
                .createdOn(order.getCreatedOn())
                .isPaid(order.isPaid())
                .isDelivered(order.isDelivered())
                .build();
    }
}
