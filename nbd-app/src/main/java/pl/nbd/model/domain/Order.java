package pl.nbd.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.javamoney.moneta.Money;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order extends AbstractEntity {

    private long id;

    private Client client;

    private Address shippingAddress;

    private Map<Long, Item> orderItems;

    private Money orderValue;

    private Date createdOn;

    private boolean isPaid;

    private boolean isDelivered;

    public Order(UUID uniqueId, long id, Client client, Address shippingAddress, Map<Long, Item> orderItems, Money orderValue, Date createdOn, boolean isPaid, boolean isDelivered) {
        super(uniqueId);
        this.id = id;
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Order(long id, Client client, Address shippingAddress, Map<Long, Item> orderItems, Money orderValue, Date createdOn, boolean isPaid, boolean isDelivered) {
        this.id = id;
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }
}
