package pl.nbd.manager;

import pl.nbd.model.domain.Item;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemManager {

    Item addItem(Item item);

    Item updateItem(Item item);

    void deleteItem(Item item);

    List<Item> findAllItems();

    Optional<Item> findItemById(UUID id);

}
