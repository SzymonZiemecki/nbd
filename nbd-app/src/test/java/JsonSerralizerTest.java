import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;
import pl.nbd.Provider.ItemSerializer;
import pl.nbd.model.domain.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JsonSerralizerTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void serializeTest() throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);



        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 5, "PC","intel", "DLUGI opis", 30.5d, true,"laptop");
        Item item2 = new Laptop(UUID.randomUUID(),"intel i7", 30l, 30l, 5, "PC","intel", "DLUGI opis", 30.5d, true,"laptop");
        Map<UUID, Long> orderItems = new HashMap<>();
        orderItems.put(item.getUniqueId(), 3l);
        orderItems.put(item2.getUniqueId(), 3l);
        ItemsQuantityMap itemsQuantityMap = ItemsQuantityMap.of(orderItems);

        Order order  = new Order();
        order.setClient(UUID.randomUUID());
        order.setUniqueId(UUID.randomUUID());
        order.setShippingAddress(new Address("test","test","test"));
        order.setOrderItems(itemsQuantityMap);
        order.setOrderValue(20d);
        order.setCreatedOn(new Date());
        order.setPaid(true);
        order.setDelivered(false);
        System.out.println(mapper.writeValueAsString(order));

        String str = mapper.writeValueAsString(order);
        Order orderR = mapper.readValue(str, Order.class);
    }
}
