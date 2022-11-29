package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class MonitorJson extends ItemJson {
    @JsonbProperty("resolution")
    private String resolution;
    @JsonbProperty("panel")
    private String panel;
    @JsonbProperty("diagonal")
    private String diagonal;

    public MonitorJson(@JsonbProperty("available_amount") long availableAmount,
                       @JsonbProperty("name") String name,
                       @JsonbProperty("producer") String producer,
                       @JsonbProperty("description") String description,
                       @JsonbProperty("price") Double price,
                       @JsonbProperty("resolution") String resolution,
                       @JsonbProperty("panel") String panel,
                       @JsonbProperty("diagonal") String diagonal) {
        super(availableAmount, name, producer, description, price);
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
