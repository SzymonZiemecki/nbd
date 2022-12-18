package pl.nbd.Ids;

import com.datastax.oss.driver.api.core.CqlIdentifier;

public class ClientIds {
    public static final CqlIdentifier SHOP_APP_NAMESPACE = CqlIdentifier.fromCql("shop_app");
    public static final CqlIdentifier UNIQUIE_ID = CqlIdentifier.fromCql("unique_id");
    public static final CqlIdentifier NAME = CqlIdentifier.fromCql("name");
    public static final CqlIdentifier SURNAME = CqlIdentifier.fromCql("surname");
    public static final CqlIdentifier ADDRESS = CqlIdentifier.fromCql("address");
    public static final CqlIdentifier ACCOUNT_BALANCE = CqlIdentifier.fromCql("account_balance");
    public static final CqlIdentifier IS_SUSPENDED = CqlIdentifier.fromCql("is_suspended");
}
