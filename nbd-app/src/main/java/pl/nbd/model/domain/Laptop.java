package pl.nbd.model.domain;

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
public class Laptop extends Item{

    private String cpu;

    private long ramAmount;

    private long memoryAmount;

    public Laptop(String cpu, long ramAmount, long memoryAmount) {
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }

    public Laptop(long availableAmount, String name, String producer, String description, Double price, String cpu, long ramAmount, long memoryAmount) {
        super(availableAmount, name, producer, description, price);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }
}
