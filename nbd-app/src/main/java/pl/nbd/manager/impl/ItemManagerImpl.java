package pl.nbd.manager.impl;

import pl.nbd.manager.ItemManager;
import pl.nbd.model.Item;
import pl.nbd.repository.ItemRepository;

import java.util.List;

public class ItemManagerImpl implements ItemManager {
    private ItemRepository itemRepository;

    public ItemManagerImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item addItem(Item item) {
        return itemRepository.add(item);
    }

    @Override
    public Item updateItem(Item item){
        return itemRepository.update(item);
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.remove(item);
    }

    @Override
    public List<Item> findAllAddresses(){
        return itemRepository.findAll();
    }

    @Override
    public Item findItemById(Long id){
        return itemRepository.findById(id);
    }

}
