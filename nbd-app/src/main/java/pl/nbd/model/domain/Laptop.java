package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity(defaultKeyspace = "shop_app")
@PropertyStrategy(mutable = false)
@CqlName("items")
@NamingStrategy(convention = NamingConvention.SNAKE_CASE_INSENSITIVE)
@EqualsAndHashCode
public class Laptop extends Item{

    private String cpu;

    @CqlName("ram_amount")
    private long ramAmount;

    @CqlName("memory_amount")
    private long memoryAmount;

    public Laptop(UUID uniqueId, String cpu, long ramAmount, long memoryAmount, long availableAmount, String name, String producer, String description, Double price, boolean available, String discriminator) {
        super(uniqueId, availableAmount, name, producer, description, price, available, "laptop");
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }
}
