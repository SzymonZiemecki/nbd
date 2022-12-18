package pl.nbd.model.domain;


import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
@EqualsAndHashCode
public class Address{
    private String city;
    private String street;
    private String localNumber;

    public Address(String city, String street, String localNumber) {
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }
}
