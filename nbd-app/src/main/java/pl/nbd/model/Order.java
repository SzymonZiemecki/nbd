package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;
import pl.nbd.converter.ItemListConverter;
import pl.nbd.converter.MoneyConverter;

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
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_items",
            joinColumns = {@JoinColumn(name = "order_id", referencedColumnName = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "item_id")})
    @MapKey(name = "name")
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
