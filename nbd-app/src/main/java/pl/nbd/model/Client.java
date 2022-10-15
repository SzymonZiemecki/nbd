package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.javamoney.moneta.Money;
import pl.nbd.converter.MoneyConverter;

import java.util.UUID;

@Entity
@Table(name = "client")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client extends AbstractEntity{

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String surname;

    @NotNull
    @JoinColumn(name = "address_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @NotNull
    @Convert(converter = MoneyConverter.class)
    @Column(name = "account_balance")
    private Money accountBalance;

    public Client(String name, String surname, Address address, Money accountBalance) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.accountBalance = accountBalance;
    }
}
