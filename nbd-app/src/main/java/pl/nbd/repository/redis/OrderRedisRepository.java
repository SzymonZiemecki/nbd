package pl.nbd.repository.redis;

import pl.nbd.model.redis.OrderJson;
import pl.nbd.repository.AbstractRedisRepository;

public class OrderRedisRepository extends AbstractRedisRepository<OrderJson> {
    public OrderRedisRepository() {
        super(OrderJson.class, "order:");
    }
}
