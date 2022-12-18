package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@Entity
@CqlName("clients")
@PropertyStrategy(mutable = false)
@EqualsAndHashCode
public class Client extends AbstractEntity {
    private String name;

    private String surname;

    private Address address;

    private Double accountBalance;

    @CqlName("is_suspended")
    private boolean suspened;


    public Client(UUID uniqueId, String name, String surname, Address address, Double accountBalance, boolean isSuspened) {
        super(uniqueId);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.suspened = isSuspened;
    }

    public Client(String name, String surname, Address address, Double accountBalance, boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.suspened = isSuspened;
    }
}
