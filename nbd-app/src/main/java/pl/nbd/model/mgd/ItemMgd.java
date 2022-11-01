package pl.nbd.model.mgd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.javamoney.moneta.Money;

@BsonDiscriminator(key = "_clazz")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@SuperBuilder
public abstract class ItemMgd extends AbstractEntityMgd{
    @BsonProperty("available_amount")
    private long availableAmount;
    @BsonProperty("name")
    private String name;
    @BsonProperty("producer")
    private String producer;
    @BsonProperty("description")
    private String description;
    @BsonProperty("price")
    private Double price;


    @BsonCreator
    public ItemMgd(@BsonProperty("available_amount") long availableAmount,
                   @BsonProperty("name")String name,
                   @BsonProperty("producer")String producer,
                   @BsonProperty("description")String description,
                   @BsonProperty("price")Double price) {
        this.availableAmount = availableAmount;
        this.name = name;
        this.producer = producer;
        this.description = description;
        this.price = price;
    }
}
