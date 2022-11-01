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
public class Address extends AbstractEntity{
    private String city;
    private String street;
    private String localNumber;

    public Address(UUID uniqueId, String city, String street, String localNumber) {
        super(uniqueId);
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }

    public Address(String city, String street, String localNumber) {
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }
}
