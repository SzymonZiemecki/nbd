package pl.nbd.repository;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pl.nbd.model.redis.AbstractEntityJson;
import redis.clients.jedis.*;

import java.util.*;

public class AbstractRedisRepository<T extends AbstractEntityJson> implements RepositoryIf<T> {

    private static JedisPooled pool;
    private static Jsonb jsonb = JsonbBuilder.create();
    protected Class<T> clazz;
    private String prefix;

    public void initConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPooled(new HostAndPort("localhost", 6379), clientConfig);
    }

    public AbstractRedisRepository(Class<T> clazz, String prefix) {
        this.clazz = clazz;
        this.prefix = prefix;
        initConnection();
    }

    public boolean checkConnection() {
        return pool.getPool().getResource().isConnected();
    }

    public void clearCache(){
        Set<String> keys = pool.keys("*");
        for (String key : keys){
            pool.del(key);
        }
    }

    public void clearThis(){
        Set<String> keys = pool.keys(prefix + "*");
        for (String key : keys){
            pool.del(key);
        }
    }

    @Override
    public Optional<T> findById(UUID id) {
        Optional<String> found = Optional.of(pool.get(prefix + id.toString()));
        T foundObj = jsonb.fromJson(found.get(), this.clazz);
        return Optional.of(foundObj);
    }

    @Override
    public T add(T entity) {
        String entityStr = jsonb.toJson(entity);
        pool.set(prefix + entity.getUniqueId().toString(), entityStr);

        return entity;
    }

    @Override
    public void remove(T entity) {
        pool.del(prefix + entity.getUniqueId().toString());
    }

    @Override
    public T update(T updatedEntity) {
        String entityStr = jsonb.toJson(updatedEntity);
        pool.set(prefix + updatedEntity.getUniqueId().toString(), entityStr);

        return updatedEntity;
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<T> findAll() {
        List<T> all = new ArrayList<>();
        Set<String> keys = pool.keys(prefix + "*");
        for (String key : keys){
            all.add(jsonb.fromJson(pool.get(key), this.clazz));
        }

        return all;
    }

    @Override
    public void close() throws Exception {
        pool.getPool().destroy();
        pool.close();
    }
}
