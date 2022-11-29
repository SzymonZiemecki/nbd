package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@SuperBuilder
public class ClientJson extends AbstractEntityJson {
    @JsonbProperty("name")
    private String name;

    @JsonbProperty("surname")
    private String surname;

    @JsonbProperty("address")
    private AddressJson address;

    @JsonbProperty("account_balance")
    private Double accountBalance;

    @JsonbProperty("is_suspended")
    private Boolean isSuspened;

    public ClientJson(@JsonbProperty("name") String name,
                      @JsonbProperty("surname") String surname,
                      @JsonbProperty("address") AddressJson address,
                      @JsonbProperty("account_balance") Double accountBalance,
                      @JsonbProperty("is_suspended") Boolean isSuspened) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
        this.isSuspened = isSuspened;
    }
    @JsonbCreator
    public ClientJson(@JsonbProperty("_id") UUID uniqueId,
                      @JsonbProperty("name") String name,
                      @JsonbProperty("surname") String surname,
                      @JsonbProperty("address") AddressJson address,
                      @JsonbProperty("account_balance") Double accountBalance,
                      @JsonbProperty("is_suspended") Boolean isSuspened) {
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
