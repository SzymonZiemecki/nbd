package pl.nbd.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public abstract class Item extends AbstractEntity {
    private long availableAmount;

    private String name;

    private String producer;

    private String description;

    private Money price;

    private boolean available;

    public Item(UUID uniqueId, long availableAmount, String name, String producer, String description, Money price, boolean available) {
        super(uniqueId);
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.available = available;
    }

}
