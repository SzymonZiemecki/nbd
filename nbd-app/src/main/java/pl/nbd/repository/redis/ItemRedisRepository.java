package pl.nbd.repository.redis;

import pl.nbd.model.redis.ItemJson;
import pl.nbd.repository.AbstractRedisRepository;

public class ItemRedisRepository extends AbstractRedisRepository<ItemJson> {
    public ItemRedisRepository() {
        super(ItemJson.class, "item:");
    }
}
