package pl.nbd.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.javamoney.moneta.Money;
import pl.nbd.model.Item;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;


@Converter
public class ItemListConverter implements AttributeConverter<List<Item>, String> {
    private static final String SPLIT_CHAR = ";";


    @Override
    public String convertToDatabaseColumn(List<Item> items) {
        List<String> itemIds = items.stream()
                .map( item -> item.getId())
                .map( itemId -> itemId.toString())
                .collect(Collectors.toList());
        return itemIds != null ? String.join(SPLIT_CHAR, itemIds) : "";
    }

    @Override
    public List<Item> convertToEntityAttribute(String string) {
        return emptyList();
    }
}
