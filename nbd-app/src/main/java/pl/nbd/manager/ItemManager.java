package pl.nbd.manager;

import pl.nbd.model.Item;
import pl.nbd.repository.ItemRepository;

import java.util.List;

public interface ItemManager {

    public Item addItem(Item item);

    public Item updateItem(Item item);

    public void deleteItem(Item item);

    public List<Item> findAllAddresses();

    public Item findItemById(Long id);

}
