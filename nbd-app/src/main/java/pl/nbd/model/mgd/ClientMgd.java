package pl.nbd.model.mgd;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
public class ClientMgd extends AbstractEntityMgd {
    @BsonProperty("name")
    private String name;

    @BsonProperty("surname")
    private String surname;

    @BsonProperty("address")
    private AddressMgd address;

    @BsonProperty("account_balance")
    private Double accountBalance;

    @BsonProperty("is_suspended")
    private Boolean isSuspened;

    public ClientMgd(@BsonProperty("name") String name,
                     @BsonProperty("surname") String surname,
                     @BsonProperty("address") AddressMgd address,
                     @BsonProperty("account_balance") Double accountBalance,
                     @BsonProperty("is_suspended") Boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }
    @BsonCreator
    public ClientMgd(@BsonProperty("_id") UUID uniqueId,
                     @BsonProperty("name") String name,
                     @BsonProperty("surname") String surname,
                     @BsonProperty("address") AddressMgd address,
                     @BsonProperty("account_balance") Double accountBalance,
                     @BsonProperty("is_suspended") Boolean isSuspened) {
        super(uniqueId);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }

    public Boolean isSuspened() {
        return isSuspened;
    }
}
