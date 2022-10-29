package pl.nbd.model.mgd;

import lombok.*;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.nbd.model.domain.AbstractEntityMgd;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClientMgd extends AbstractEntityMgd {
    @BsonProperty("name")
    private String name;

    @BsonProperty("surname")
    private String surname;

    @BsonProperty("address")
    private AddressMgd address;

    @BsonProperty("account_balance")
    private Long accountBalance;

    @BsonProperty("is_suspended")
    private boolean isSuspened;

    @BsonCreator
    public ClientMgd(String name, String surname, AddressMgd address, Long accountBalance, boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }

    public ClientMgd(UUID uniqueId, String name, String surname, AddressMgd address, Long accountBalance, boolean isSuspened) {
        super(uniqueId);
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }
}
