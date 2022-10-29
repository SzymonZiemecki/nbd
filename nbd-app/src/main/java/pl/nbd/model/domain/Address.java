package pl.nbd.model.domain;


import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
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
}
