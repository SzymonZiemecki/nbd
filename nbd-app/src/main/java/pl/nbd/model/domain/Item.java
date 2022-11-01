package pl.nbd.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
public abstract class Item extends AbstractEntity {
    private long availableAmount;

    private String name;

    private String producer;

    private String description;

    private Double price;

    private Boolean available;

    public Item(long availableAmount, String name, String producer, String description, Double price) {
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
    }

}
