package pl.nbd.model.mgd;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
@SuperBuilder
public abstract class AbstractEntityMgd implements Serializable {

    @BsonProperty("_id")
    protected UUID uniqueId;

    @BsonCreator
    public AbstractEntityMgd(@BsonProperty("_id") UUID uniqueId) {
         this.uniqueId = uniqueId;
    }
}
