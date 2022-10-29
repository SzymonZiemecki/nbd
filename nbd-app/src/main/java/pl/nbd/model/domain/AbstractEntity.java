package pl.nbd.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Serializable {
    protected UUID uniqueId = UUID.randomUUID();
}