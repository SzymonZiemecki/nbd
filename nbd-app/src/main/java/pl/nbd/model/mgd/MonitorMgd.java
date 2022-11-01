package pl.nbd.model.mgd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class MonitorMgd extends ItemMgd{
    @BsonProperty("resolution")
    private String resolution;
    @BsonProperty("panel")
    private String panel;
    @BsonProperty("diagonal")
    private String diagonal;

    public MonitorMgd(@BsonProperty("available_amount") long availableAmount,
                      @BsonProperty("name") String name,
                      @BsonProperty("producer") String producer,
                      @BsonProperty("description") String description,
                      @BsonProperty("price") Double price,
                      @BsonProperty("resolution") String resolution,
                      @BsonProperty("panel") String panel,
                      @BsonProperty("diagonal") String diagonal) {
        super(availableAmount, name, producer, description, price);
        this.resolution = resolution;
        this.panel = panel;
        this.diagonal = diagonal;
    }
}
