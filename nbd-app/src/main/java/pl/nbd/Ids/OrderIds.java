package pl.nbd.Ids;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class OrderIds {
    public static final CqlIdentifier SHOP_APP_NAMESPACE = CqlIdentifier.fromCql("shop_app");
    public static final CqlIdentifier ORDERS_BY_CLIENT = CqlIdentifier.fromCql("orders_by_client");
    public static final CqlIdentifier ORDERS_BY_ITEM = CqlIdentifier.fromCql("orders_by_item");
    public static final CqlIdentifier UNIQUIE_ID = CqlIdentifier.fromCql("unique_id");
    public static final CqlIdentifier CLIENT_ID = CqlIdentifier.fromCql("client_id");
    public static final CqlIdentifier SHIPPING_ADDRESS = CqlIdentifier.fromCql("shipping_address");
    public static final CqlIdentifier ORDER_ITEMS = CqlIdentifier.fromCql("order_items");
    public static final CqlIdentifier ORDER_VALUE = CqlIdentifier.fromCql("order_value");
    public static final CqlIdentifier CREATED_ON = CqlIdentifier.fromCql("created_on");
    public static final CqlIdentifier IS_PAID = CqlIdentifier.fromCql("paid");
    public static final CqlIdentifier IS_DELIVERED = CqlIdentifier.fromCql("delivered");

    public static final CqlIdentifier OBJECT = CqlIdentifier.fromCql("object");
}
