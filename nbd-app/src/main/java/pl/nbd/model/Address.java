package pl.nbd.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
@Access(AccessType.FIELD)
@ToString
public class Address extends AbstractEntity{
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "city")
    @NotNull
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "localNumber")
    private String localNumber;

    public Address(String city, String street, String localNumber) {
        this.city = city;
        this.street = street;
        this.localNumber = localNumber;
    }
}
