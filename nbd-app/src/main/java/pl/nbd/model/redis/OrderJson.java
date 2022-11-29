package pl.nbd.model.redis;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class OrderJson extends AbstractEntityJson {
    @JsonbProperty("client")
    private ClientJson client;
    @JsonbProperty("shipping_address")
    private AddressJson shippingAddress;
    @JsonbProperty("order_items")
    private Map<String, ItemJson> orderItems;
    @JsonbProperty("order_value")
    private Double orderValue;
    @JsonbProperty("created_on")
    private Date createdOn;
    @JsonbProperty("is_paid")
    private Boolean isPaid;
    @JsonbProperty("is_delivered")
    private Boolean isDelivered;

    @JsonbCreator
    public OrderJson(@JsonbProperty("client") ClientJson client,
                     @JsonbProperty("shipping_address") AddressJson shippingAddress,
                     @JsonbProperty("order_items") Map<String, ItemJson> orderItems,
                     @JsonbProperty("order_value") Double orderValue,
                     @JsonbProperty("created_on") Date createdOn,
                     @JsonbProperty("is_paid") Boolean isPaid,
                     @JsonbProperty("is_delivered") Boolean isDelivered) {
        this.client = client;
        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.orderValue = orderValue;
        this.createdOn = createdOn;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
    }

    public OrderJson(UUID uniqueId, ClientJson client, AddressJson shippingAddress, Map<String, ItemJson> orderItems, Double orderValue, Date createdOn, Boolean isPaid, Boolean isDelivered) {
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
