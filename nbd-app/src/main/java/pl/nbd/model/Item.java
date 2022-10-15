package pl.nbd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

@Entity(name="item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
@Getter
@Setter
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

    @Column
    private Money price;
}
