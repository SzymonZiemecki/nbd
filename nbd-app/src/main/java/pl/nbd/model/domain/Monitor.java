package pl.nbd.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Monitor extends Item {
    private String resolution;

    private String panel;

    private String diagonal;

    public Monitor(String resolution, String panel, String diagonal) {
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }

    public Monitor(UUID uniqueId, long availableAmount, String name, String producer, String description, Money price, boolean available, String resolution, String panel, String diagonal) {
        super(uniqueId, availableAmount, name, producer, description, price, available);
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
