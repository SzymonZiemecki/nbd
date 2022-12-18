/*
package pl.nbd.manager.impl;

import pl.nbd.manager.ItemManager;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Item;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mapper.ItemMapper;
import pl.nbd.repository.mongo.ItemMgdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemManagerImpl implements ItemManager {
    private ItemMgdRepository itemRepository;

    public ItemManagerImpl() {
        this.itemRepository = new ItemMgdRepository();
    }

    @Override
    public Item addItem(Item item) {
        return ItemMapper.toDomainModel(itemRepository.add(ItemMapper.toMongoDocument(item)));
    }

    @Override
    public Item updateItem(Item item){
        return ItemMapper.toDomainModel(itemRepository.update(ItemMapper.toMongoDocument(item)));
    }

    @Override
    public void deleteItem(Item item) {
        itemRepository.remove(ItemMapper.toMongoDocument(item));
    }

    @Override
    public List<Item> findAllItems(){
        List<Item> items = new ArrayList<>();
        itemRepository.findAll().stream().forEach(item -> items.add(ItemMapper.toDomainModel(item)));
        return items;
    }

    @Override
    public Optional<Item> findItemById(UUID id){
        return Optional.ofNullable(ItemMapper.toDomainModel(itemRepository.findById(id).get()));
    }

}
*/
