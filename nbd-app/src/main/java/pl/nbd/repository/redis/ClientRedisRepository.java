package pl.nbd.repository.redis;

import pl.nbd.model.redis.ClientJson;
import pl.nbd.repository.AbstractRedisRepository;

public class ClientRedisRepository extends AbstractRedisRepository<ClientJson> {
    public ClientRedisRepository() {
        super(ClientJson.class, "client:");
    }
}
