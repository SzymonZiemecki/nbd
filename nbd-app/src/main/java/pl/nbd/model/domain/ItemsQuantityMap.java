package pl.nbd.model.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.nbd.Provider.ItemSerializer;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class ItemsQuantityMap {
    private Map<UUID, Long> items;

    public ItemsQuantityMap() {
    }

    public ItemsQuantityMap(Map<UUID, Long> items) {
        this.items = items;
    }

    public static ItemsQuantityMap  of(Map<UUID, Long> items){
        return new ItemsQuantityMap(items);
    }
}
