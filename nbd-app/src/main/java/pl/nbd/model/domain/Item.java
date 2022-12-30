package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
@Entity(defaultKeyspace = "shop_app")
@PropertyStrategy(mutable = false)
@CqlName("items")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Monitor.class, name = "Monitor"),

        @JsonSubTypes.Type(value = Laptop.class, name = "Laptop") }
)
public class Item extends AbstractEntity {
    @CqlName("available_amount")
    private long availableAmount;

    private String name;

    private String producer;

    private String description;

    private Double price;

    @CqlName("is_available")
    private Boolean available;

    private String discriminator;
    public Item(long availableAmount, String name, String producer, String description, Double price, Boolean available, String discriminator) {
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.available = available;
        this.discriminator = discriminator;
    }

    public Item(UUID uniqueId, long availableAmount, String name, String producer, String description, Double price, Boolean available, String discriminator) {
        super(uniqueId);
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
        this.available = available;
        this.discriminator = discriminator;
    }
}
