package pl.nbd.Ids;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ItemIds {
    public static final CqlIdentifier SHOP_APP_NAMESPACE = CqlIdentifier.fromCql("shop_app");
    public static final CqlIdentifier TABLE_NAME = CqlIdentifier.fromCql("items");
    public static final CqlIdentifier UNIQUIE_ID = CqlIdentifier.fromCql("unique_id");
    public static final CqlIdentifier AVAILABLE_AMOUNT = CqlIdentifier.fromCql("available_amount");
    public static final CqlIdentifier NAME = CqlIdentifier.fromCql("name");
    public static final CqlIdentifier PRODUCER = CqlIdentifier.fromCql("producer");
    public static final CqlIdentifier DESCRIPTION = CqlIdentifier.fromCql("description");
    public static final CqlIdentifier PRICE = CqlIdentifier.fromCql("price");
    public static final CqlIdentifier AVAILABLE = CqlIdentifier.fromCql("is_available");
    public static final CqlIdentifier DISCRIMINATOR = CqlIdentifier.fromCql("discriminator");
    public static final CqlIdentifier RESOLUTION = CqlIdentifier.fromCql("resolution");
    public static final CqlIdentifier PANEL = CqlIdentifier.fromCql("panel");
    public static final CqlIdentifier DIAGONAL = CqlIdentifier.fromCql("diagonal");
    public static final CqlIdentifier CPU = CqlIdentifier.fromCql("cpu");
    public static final CqlIdentifier RAM_AMOUNT = CqlIdentifier.fromCql("ram_amount");
    public static final CqlIdentifier MEMORY_AMOUNT = CqlIdentifier.fromCql("memory_amount");
}
