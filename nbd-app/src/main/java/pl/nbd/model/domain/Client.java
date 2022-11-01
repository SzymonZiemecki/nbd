package pl.nbd.model.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class Client extends AbstractEntity {
    private String name;

    private String surname;

    private Address address;

    private Double accountBalance;

    private boolean isSuspened;


    public Client(UUID uniqueId, String name, String surname, Address address, Double accountBalance, boolean isSuspened) {
        super(uniqueId);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }

    public Client(String name, String surname, Address address, Double accountBalance, boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }
}
