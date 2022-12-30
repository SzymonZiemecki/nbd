package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
@Entity(defaultKeyspace = "shop_app")
@PropertyStrategy(mutable = false)
@CqlName("items")
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
@JsonTypeName("monitor")
public class Monitor extends Item {
    private String resolution;

    private String panel;

    private String diagonal;

    public Monitor(UUID uniqueId, long availableAmount, String name, String producer, String description, Double price, Boolean available, String discriminator, String resolution, String panel, String diagonal) {
        super(uniqueId, availableAmount, name, producer, description, price, available, "monitor");
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }

    public Monitor(UUID uniqueId, String resolution, String panel, String diagonal, long availableAmount, String name, String producer, String description, Double price, boolean available, String discriminator) {
        super(uniqueId, availableAmount, name, producer, description, price, available, "monitor");
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
