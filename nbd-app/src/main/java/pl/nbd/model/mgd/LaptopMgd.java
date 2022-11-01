package pl.nbd.model.mgd;

import jakarta.persistence.DiscriminatorValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

@BsonDiscriminator(key = "_clazz", value = "laptop")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class LaptopMgd extends ItemMgd {
    @BsonProperty("cpu")
    private String cpu;
    @BsonProperty("ram_amount")
    private long ramAmount;
    @BsonProperty("memory_amount")
    private long memoryAmount;

    public LaptopMgd(String cpu, long ramAmount, long memoryAmount) {
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }

    @BsonCreator
    public LaptopMgd(@BsonProperty("available_amount") long availableAmount,
                     @BsonProperty("name") String name,
                     @BsonProperty("producer") String producer,
                     @BsonProperty("description") String description,
                     @BsonProperty("price") Double price,
                     @BsonProperty("cpu") String cpu,
                     @BsonProperty("ram_amount") long ramAmount,
                     @BsonProperty("memory_amount") long memoryAmount) {
        super(availableAmount, name, producer, description, price);
        this.cpu = cpu;
        this.ramAmount = ramAmount;
        this.memoryAmount = memoryAmount;
    }
}
