package pl.nbd.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.javamoney.moneta.Money;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Order extends AbstractEntity {
    private Client client;

    private Address shippingAddress;

    private Map<String, Item> orderItems;

    private Double orderValue;

    private Date createdOn;

    private boolean isPaid;

    private boolean isDelivered;

    public Order(UUID uniqueId, Client client, Address shippingAddress, Map<String, Item> orderItems, Double orderValue, Date createdOn, boolean isPaid, boolean isDelivered) {
        super(uniqueId);
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Order(Client client, Address shippingAddress, Map<String, Item> orderItems, Double orderValue, boolean isPaid, boolean isDelivered) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = new Date();
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }
}
