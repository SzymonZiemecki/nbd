package pl.nbd.model.redis;


import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
@SuperBuilder
public abstract class AbstractEntityJson implements Serializable {

    @JsonbProperty("_id")
    protected UUID uniqueId;

    @JsonbCreator
    public AbstractEntityJson(@JsonbProperty("_id") UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}

