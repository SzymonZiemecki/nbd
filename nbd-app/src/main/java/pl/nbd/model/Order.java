package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.javamoney.moneta.Money;
import pl.nbd.converter.MoneyConverter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "order_items",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "item_id")})
    @MapKeyColumn(name = "quantity")
    private Map<Long, Item> orderItems;

    @NotNull
    @Convert(converter = MoneyConverter.class)
    @Column(name = "order_value")
    private Money orderValue;

    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "is_delivered")
    private boolean isDelivered;

    public Order(Client client, Address shippingAddress, Map<Long, Item> items, Money orderValue, boolean isPaid, boolean isDelivered) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = items;
        this.orderValue = orderValue;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

}
