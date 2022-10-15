package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;
import pl.nbd.converter.MoneyConverter;

import java.util.List;

@Entity
@Table(name = "orders")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_items",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "item_id")})
    private List<Item> orderItems;

    @NotNull
    @Convert(converter = MoneyConverter.class)
    @Column(name = "order_value")
    private Money orderValue;

    @Column
    private boolean isPaid;

    public Order(Client client, Address shippingAddress, List<Item> items, Money orderValue, boolean isPaid) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = items;
        this.orderValue = orderValue;
        this.isPaid = isPaid;
    }

}
