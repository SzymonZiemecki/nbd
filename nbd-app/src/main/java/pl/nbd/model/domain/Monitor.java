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
public class Monitor extends Item {
    private String resolution;

    private String panel;

    private String diagonal;

    public Monitor(String resolution, String panel, String diagonal) {
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }

    public Monitor(long availableAmount, String name, String producer, String description, Double price, String resolution, String panel, String diagonal) {
        super(availableAmount, name, producer, description, price);
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
