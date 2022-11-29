package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class AddressJson extends AbstractEntityJson {
    @JsonbProperty("city")
    private String city;
    @JsonbProperty("street")
    private String street;
    @JsonbProperty("local_number")
    private String localNumber;

    @JsonbCreator
    public AddressJson(@JsonbProperty("city") String city,
                       @JsonbProperty("street") String street,
                       @JsonbProperty("local_number") String localNumber) {
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }

    public AddressJson(@JsonbProperty("_id") UUID uniqueId,
                       @JsonbProperty("city") String city,
                       @JsonbProperty("street") String street,
                       @JsonbProperty("local_number") String localNumber) {
        super(uniqueId);
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }
}
