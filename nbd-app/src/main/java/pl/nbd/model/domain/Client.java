package pl.nbd.model.domain;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client extends AbstractEntity {
    private String name;

    private String surname;

    private Address address;

    private Long accountBalance;

    private boolean isSuspened;


    public Client(UUID uniqueId, String name, String surname, Address address, Long accountBalance, boolean isSuspened) {
        super(uniqueId);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }

    public Client(String name, String surname, Address address, Long accountBalance, boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }
}
