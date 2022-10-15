package pl.nbd.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Access(AccessType.FIELD)
public class Client {

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private String surname;

    public Client() {

    }
}
