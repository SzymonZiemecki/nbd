package pl.nbd.manager;

import pl.nbd.model.Address;
import pl.nbd.model.Item;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ItemRepository;

import java.util.List;

public class ItemManager {
    private ItemRepository itemRepository;

    public ItemManager(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item) {
        return itemRepository.add(item);
    }

    public Item updateItem(Item item){
        return itemRepository.update(item);
    }

    public void deleteItem(Item item) {
        itemRepository.remove(item);
    }

    public List<Item> findAllAddresses(){
        return itemRepository.findAll();
    }

    public Item findItemById(Long id){
        return itemRepository.findById(id);
    }

}
