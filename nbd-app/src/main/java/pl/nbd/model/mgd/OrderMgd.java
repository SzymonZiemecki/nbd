package pl.nbd.model.mgd;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.javamoney.moneta.Money;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class OrderMgd extends AbstractEntityMgd {
    @BsonProperty("client")
    private ClientMgd client;
    @BsonProperty("shipping_address")
    private AddressMgd shippingAddress;
    @BsonProperty("order_items")
    private Map<String, ItemMgd> orderItems;
    @BsonProperty("order_value")
    private Double orderValue;
    @BsonProperty("created_on")
    private Date createdOn;
    @BsonProperty("is_paid")
    private Boolean isPaid;
    @BsonProperty("is_delivered")
    private Boolean isDelivered;

    @BsonCreator
    public OrderMgd(@BsonProperty("client") ClientMgd client,
                    @BsonProperty("shipping_address") AddressMgd shippingAddress,
                    @BsonProperty("order_items") Map<String, ItemMgd> orderItems,
                    @BsonProperty("order_value") Double orderValue,
                    @BsonProperty("created_on") Date createdOn,
                    @BsonProperty("is_paid") Boolean isPaid,
                    @BsonProperty("is_delivered") Boolean isDelivered) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public OrderMgd(UUID uniqueId, ClientMgd client, AddressMgd shippingAddress, Map<String, ItemMgd> orderItems, Double orderValue, Date createdOn, Boolean isPaid, Boolean isDelivered) {
        super(uniqueId);
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public Boolean isPaid(){
        return isPaid;
    }

    public Boolean isDelivered(){
        return isDelivered;
    }
}
