package pl.nbd.model.domain;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractEntity implements Serializable {
    @PartitionKey
    protected UUID uniqueId;
}