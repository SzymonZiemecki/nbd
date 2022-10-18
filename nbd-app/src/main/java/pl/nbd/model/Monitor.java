package pl.nbd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.javamoney.moneta.Money;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("monitor")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Monitor extends Item {
    @Column
    private String resolution;

    @Column
    private String panel;

    @Column
    private String diagonal;

    public Monitor(long availableAmount, String name, String producer, String description, Money price, String resolution, String panel, String diagonal) {
        super(availableAmount, name, producer, description, price);
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
