package pl.nbd.manager;

import pl.nbd.model.Item;
import pl.nbd.repository.ItemRepository;

import java.util.List;

public interface ItemManager {

    Item addItem(Item item);

    Item updateItem(Item item);

    void deleteItem(Item item);

    List<Item> findAllItems();

    Item findItemById(Long id);

}
