package pl.nbd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@Access(AccessType.FIELD)
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @Column(name = "unique_id")
    @NotNull
    protected UUID uniqueId = UUID.randomUUID();

    @Version
    @NotNull
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    protected long version;
}
