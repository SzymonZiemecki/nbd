package pl.nbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javamoney.moneta.Money;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("laptop")
@Getter
@Setter
@NoArgsConstructor
public class Laptop extends Item{

    @Column
    private String cpu;

    @Column
    private long ramAmmount;

    @Column
    private long memoryAmount;

    public Laptop(long availableAmount, String name, String producer, String description, Money price, String cpu, long ramAmmount, long memoryAmount) {
        super(availableAmount, name, producer, description, price);
        this.cpu = cpu;
        this.ramAmmount = ramAmmount;
        this.memoryAmount = memoryAmount;
    }
}
