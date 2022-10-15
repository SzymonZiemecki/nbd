package pl.nbd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Access(AccessType.FIELD)
@DiscriminatorValue("laptop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Laptop extends Item{

    @Column
    private String cpu;

    @Column
    private long ramAmmount;

    @Column
    private long memoryAmount;
}
