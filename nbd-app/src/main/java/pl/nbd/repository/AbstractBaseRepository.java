//package pl.nbd.repository;
//
//import pl.nbd.model.domain.AbstractEntity;
//import pl.nbd.model.mgd.AbstractEntityMgd;
//import pl.nbd.model.redis.AbstractEntityJson;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//public class AbstractBaseRepository<T extends AbstractEntity, S extends AbstractEntityJson,R extends AbstractEntityMgd> implements RepositoryIf<T>{
//
//    private AbstractRedisRepository<S> redisRepository;
//    private AbstractMongoRepository<R> mongoRepository;
//
//    public AbstractBaseRepository(AbstractMongoRepository<R> mongoRepository, AbstractRedisRepository<S> redisRepository){
//        this.mongoRepository = mongoRepository;
//        this.redisRepository = redisRepository;
//    }
//
//
//    @Override
//    Optional<T> findById(UUID id){
//        if (redisRepository.checkConnection()){
//            return redisRepository.findById(id);
//        }
//
//    }
//
//    @Override
//    T add(T entity){
//
//    }
//
//    @Override
//    void remove(T entity){
//
//    }
//
//    @Override
//    T update(T entity){
//
//    }
//
//    @Override
//    public void close() throws Exception {
//
//    }
//
//    @Override
//    long size(){
//
//    }
//
//    @Override
//    List<T> findAll(){
//
//    }
//}
