package pl.nbd.repository.base;

import pl.nbd.model.domain.Item;
import pl.nbd.model.mapper.ItemMapper;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.redis.ItemJson;
import pl.nbd.repository.AbstractMongoRepository;
import pl.nbd.repository.AbstractRedisRepository;
import pl.nbd.repository.RepositoryIf;
import pl.nbd.repository.mongo.ItemMgdRepository;
import pl.nbd.repository.redis.ClientRedisRepository;
import pl.nbd.repository.redis.ItemRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ItemRepository implements RepositoryIf<Item> {
    private ItemMgdRepository mongoRepository;
    private ItemRedisRepository redisRepository;

    public ItemRepository(ItemMgdRepository mongoRepository, ItemRedisRepository redisRepository){
        this.mongoRepository = mongoRepository;
        this.redisRepository = redisRepository;
    }



    @Override
    public Optional<Item> findById(UUID id){
        if (redisRepository.checkConnection()){
            return Optional.of(ItemMapper.toDomainModel(redisRepository.findById(id).get()));
        } else {
            return Optional.of(ItemMapper.toDomainModel(mongoRepository.findById(id).get()));
        }
    }

    @Override
    public Item add(Item item){
        redisRepository.add(ItemMapper.toRedisDocument(item));
        return ItemMapper.toDomainModel(mongoRepository.add(ItemMapper.toMongoDocument(item)));
    }

    @Override
    public void remove(Item item){
        redisRepository.remove(ItemMapper.toRedisDocument(item));
        mongoRepository.remove(ItemMapper.toMongoDocument(item));
    }

    @Override
    public Item update(Item item){
        redisRepository.update(ItemMapper.toRedisDocument(item));
        return ItemMapper.toDomainModel(mongoRepository.update(ItemMapper.toMongoDocument(item)));
    }

    @Override
    public long size(){
        return findAll().size();
    }

    @Override
    public List<Item> findAll(){
        List<Item> all = new ArrayList<>();
        if (redisRepository.checkConnection()) {
            List<ItemJson> found = redisRepository.findAll();
            for (ItemJson item: found) {
                all.add(ItemMapper.toDomainModel(item));
            }
        } else {
            List<ItemMgd> found = mongoRepository.findAll();
            for (ItemMgd item: found) {
                all.add(ItemMapper.toDomainModel(item));
            }
        }
        return all;
    }

    @Override
    public void close() throws Exception{
        mongoRepository.close();
        redisRepository.close();
    }
}
