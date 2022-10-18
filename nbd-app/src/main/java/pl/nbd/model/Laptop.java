package pl.nbd.model;

import jakarta.persistence.*;
import lombok.*;
import org.javamoney.moneta.Money;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("laptop")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Laptop extends Item{

    @Column
    private String cpu;

    @Column(name = "ram_amount")
    private long ramAmount;

    @Column(name = "memory_amount")
    private long memoryAmount;

    public Laptop(long availableAmount, String name, String producer, String description, Money price, String cpu, long ramAmmount, long memoryAmount) {
        super(availableAmount, name, producer, description, price);
        this.cpu = cpu;
        this.ramAmount = ramAmmount;
        this.memoryAmount = memoryAmount;
    }
}
