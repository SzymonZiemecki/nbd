package pl.nbd.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Laptop extends Item{

    private String cpu;

    private long ramAmount;

    private long memoryAmount;

    public Laptop(String cpu, long ramAmount, long memoryAmount) {
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }

    public Laptop(UUID uniqueId, long availableAmount, String name, String producer, String description, Money price, boolean available, String cpu, long ramAmount, long memoryAmount) {
        super(uniqueId, availableAmount, name, producer, description, price, available);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }
}
