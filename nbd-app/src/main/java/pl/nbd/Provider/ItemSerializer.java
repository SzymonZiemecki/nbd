package pl.nbd.Provider;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import pl.nbd.model.domain.Item;

import java.io.IOException;
import java.io.StringWriter;

public class ItemSerializer extends JsonSerializer<Item> {

    public static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void serialize(Item item, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("item_id");
        jsonGenerator.writeObject(item.getUniqueId());
        jsonGenerator.writeEndObject();
    }
}
