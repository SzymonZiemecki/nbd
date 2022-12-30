package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jnr.constants.platform.Local;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode
@PropertyStrategy(mutable = false)
public class Order{
    private UUID client;

    private UUID uniqueId;

    private Address shippingAddress;

    private ItemsQuantityMap orderItems;
    private Double orderValue;
    private Date createdOn;
    private boolean isPaid;
    private boolean isDelivered;

    public Order(UUID uniqueId, UUID client, Address shippingAddress, Map<UUID, Long> orderItems, Double orderValue, LocalDate createdOn, boolean isPaid, boolean isDelivered) {
        this.uniqueId = uniqueId;
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = ItemsQuantityMap.of(orderItems);
        this.orderValue = orderValue;
        this.createdOn = new Date();
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Order(UUID uniqueId, UUID client, Address shippingAddress, ItemsQuantityMap orderItems, Double orderValue, Date createdOn, boolean isPaid, boolean isDelivered) {
        this.uniqueId = uniqueId;
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Order(UUID client, Address shippingAddress, Map<UUID, Long> orderItems, Double orderValue, boolean isPaid, boolean isDelivered) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = ItemsQuantityMap.of(orderItems);
        this.orderValue = orderValue;
        this.createdOn = new Date();
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }
}
