package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class LaptopJson extends ItemJson {
    @JsonbProperty("cpu")
    private String cpu;
    @JsonbProperty("ram_amount")
    private long ramAmount;
    @JsonbProperty("memory_amount")
    private long memoryAmount;

    public LaptopJson(String cpu, long ramAmount, long memoryAmount) {
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }

    @JsonbCreator
    public LaptopJson(@JsonbProperty("available_amount") long availableAmount,
                      @JsonbProperty("name") String name,
                      @JsonbProperty("producer") String producer,
                      @JsonbProperty("description") String description,
                      @JsonbProperty("price") Double price,
                      @JsonbProperty("cpu") String cpu,
                      @JsonbProperty("ram_amount") long ramAmount,
                      @JsonbProperty("memory_amount") long memoryAmount) {
        super(availableAmount, name, producer, description, price);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }
}
