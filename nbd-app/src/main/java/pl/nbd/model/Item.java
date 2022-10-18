package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;
import pl.nbd.converter.MoneyConverter;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Access(AccessType.FIELD)
@DiscriminatorColumn(name="type")
@Getter
@Setter
@ToString
public abstract class Item extends AbstractEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    @Column
    private long availableAmount;

    @Column
    private String name;

    @Column
    private String producer;

    @Column
    private String description;

    @NotNull
    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @Column(name = "is_available")
    private boolean available;

    public Item(long availableAmount, String name, String producer, String description, Money price) {
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        if (this.availableAmount > 0) {
            setAvailable(true);
        } else {
            setAvailable(false);
        }
    }

    public Item() {

    }
}
