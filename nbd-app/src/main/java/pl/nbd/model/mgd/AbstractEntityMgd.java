package pl.nbd.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
public abstract class AbstractEntityMgd implements Serializable {

    @BsonProperty("_id")
    protected UUID uniqueId;

    public AbstractEntityMgd(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
