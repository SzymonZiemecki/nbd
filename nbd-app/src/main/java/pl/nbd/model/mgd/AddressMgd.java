package pl.nbd.model.mgd;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class AddressMgd extends AbstractEntityMgd {
    @BsonProperty("city")
    private String city;
    @BsonProperty("street")
    private String street;
    @BsonProperty("local_number")
    private String localNumber;

    @BsonCreator
    public AddressMgd(@BsonProperty("city") String city,
                      @BsonProperty("street") String street,
                      @BsonProperty("local_number") String localNumber) {
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }

    public AddressMgd(@BsonProperty("_id") UUID uniqueId,
                      @BsonProperty("city") String city,
                      @BsonProperty("street") String street,
                      @BsonProperty("local_number") String localNumber) {
        super(uniqueId);
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }
}
