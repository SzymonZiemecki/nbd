package pl.nbd.manager.impl;

import pl.nbd.manager.ItemManager;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mapper.ItemMapper;
import pl.nbd.repository.base.ItemRepository;
import pl.nbd.repository.mongo.ItemMgdRepository;
import pl.nbd.repository.redis.ItemRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemManagerImpl implements ItemManager {
    private ItemRepository itemRepository;

    public ItemManagerImpl() {
        this.itemRepository = new ItemRepository(new ItemMgdRepository(), new ItemRedisRepository());
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
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().stream().forEach(item -> items.add(item));
        return items;
    }

    @Override
    public Optional<Item> findItemById(UUID id){
        return Optional.ofNullable(itemRepository.findById(id).get());
    }

}
