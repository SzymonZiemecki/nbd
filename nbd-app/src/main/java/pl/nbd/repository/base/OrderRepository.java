package pl.nbd.repository.base;

import pl.nbd.model.domain.Order;
import pl.nbd.model.mapper.OrderMapper;
import pl.nbd.model.mgd.OrderMgd;
import pl.nbd.model.redis.OrderJson;
import pl.nbd.repository.AbstractMongoRepository;
import pl.nbd.repository.AbstractRedisRepository;
import pl.nbd.repository.RepositoryIf;
import pl.nbd.repository.mongo.OrderMgdRepository;
import pl.nbd.repository.redis.ClientRedisRepository;
import pl.nbd.repository.redis.OrderRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderRepository implements RepositoryIf<Order> {
    private OrderMgdRepository mongoRepository;
    private OrderRedisRepository redisRepository;

    public OrderRepository(OrderMgdRepository mongoRepository, OrderRedisRepository redisRepository){
        this.mongoRepository = mongoRepository;
        this.redisRepository = redisRepository;
    }



    @Override
    public Optional<Order> findById(UUID id){
        if (redisRepository.checkConnection()){
            return Optional.of(OrderMapper.toDomainModel(redisRepository.findById(id).get()));
        } else {
            return Optional.of(OrderMapper.toDomainModel(mongoRepository.findById(id).get()));
        }
    }

    @Override
    public Order add(Order order){
        redisRepository.add(OrderMapper.toRedisDocument(order));
        return OrderMapper.toDomainModel(mongoRepository.add(OrderMapper.toMongoDocument(order)));
    }

    @Override
    public void remove(Order order){
        redisRepository.remove(OrderMapper.toRedisDocument(order));
        mongoRepository.remove(OrderMapper.toMongoDocument(order));
    }

    @Override
    public Order update(Order order){
        redisRepository.update(OrderMapper.toRedisDocument(order));
        return OrderMapper.toDomainModel(mongoRepository.update(OrderMapper.toMongoDocument(order)));
    }

    @Override
    public long size(){
        return findAll().size();
    }

    @Override
    public List<Order> findAll(){
        List<Order> all = new ArrayList<>();
        if (redisRepository.checkConnection()) {
            List<OrderJson> found = redisRepository.findAll();
            for (OrderJson order: found) {
                all.add(OrderMapper.toDomainModel(order));
            }
        } else {
            List<OrderMgd> found = mongoRepository.findAll();
            for (OrderMgd order: found) {
                all.add(OrderMapper.toDomainModel(order));
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