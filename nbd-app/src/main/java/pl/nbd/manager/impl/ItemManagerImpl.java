package pl.nbd.manager.impl;

import jakarta.persistence.EntityNotFoundException;
import pl.nbd.manager.ItemManager;
import pl.nbd.model.domain.Item;
import pl.nbd.repository.cassandra.ItemRepository;

import java.util.List;
import java.util.UUID;

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
    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    @Override
    public Item findItemById(UUID id){
        return itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("entity not found"));
    }

}