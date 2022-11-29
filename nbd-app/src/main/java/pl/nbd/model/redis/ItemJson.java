package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonbTypeInfo({
        @JsonbSubtype(alias = "laptop", type = LaptopJson.class),
        @JsonbSubtype(alias = "monitor", type = MonitorJson.class)
})
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public abstract class ItemJson extends AbstractEntityJson{
    @JsonbProperty("available_amount")
    private long availableAmount;
    @JsonbProperty("name")
    private String name;
    @JsonbProperty("producer")
    private String producer;
    @JsonbProperty("description")
    private String description;
    @JsonbProperty("price")
    private Double price;


    @JsonbCreator
    public ItemJson(@JsonbProperty("available_amount") long availableAmount,
                    @JsonbProperty("name")String name,
                    @JsonbProperty("producer")String producer,
                    @JsonbProperty("description")String description,
                    @JsonbProperty("price")Double price) {
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
    }
}
