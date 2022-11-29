package pl.nbd.repository.base;

import pl.nbd.model.domain.Client;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.redis.ClientJson;
import pl.nbd.repository.AbstractMongoRepository;
import pl.nbd.repository.AbstractRedisRepository;
import pl.nbd.repository.RepositoryIf;
import pl.nbd.repository.mongo.ClientMgdRepository;
import pl.nbd.repository.redis.ClientRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements RepositoryIf<Client> {
    private ClientMgdRepository mongoRepository;
    private ClientRedisRepository redisRepository;

    public ClientRepository(ClientMgdRepository mongoRepository, ClientRedisRepository redisRepository){
        this.mongoRepository = mongoRepository;
        this.redisRepository = redisRepository;
    }



    @Override
    public Optional<Client> findById(UUID id){
        if (redisRepository.checkConnection()){
            return Optional.of(ClientMapper.toDomainModel(redisRepository.findById(id).get()));
        } else {
            return Optional.of(ClientMapper.toDomainModel(mongoRepository.findById(id).get()));
        }
    }

    @Override
    public Client add(Client client){
        redisRepository.add(ClientMapper.toRedisDocument(client));
        return ClientMapper.toDomainModel(mongoRepository.add(ClientMapper.toMongoDocument(client)));
    }

    @Override
    public void remove(Client client){
        redisRepository.remove(ClientMapper.toRedisDocument(client));
        mongoRepository.remove(ClientMapper.toMongoDocument(client));
    }

    @Override
    public Client update(Client client){
        redisRepository.update(ClientMapper.toRedisDocument(client));
        return ClientMapper.toDomainModel(mongoRepository.update(ClientMapper.toMongoDocument(client)));
    }

    @Override
    public long size(){
        return findAll().size();
    }

    @Override
    public List<Client> findAll(){
        List<Client> all = new ArrayList<>();
        if (redisRepository.checkConnection()) {
            List<ClientJson> found = redisRepository.findAll();
            for (ClientJson client: found) {
                all.add(ClientMapper.toDomainModel(client));
            }
        } else {
            List<ClientMgd> found = mongoRepository.findAll();
            for (ClientMgd client: found) {
                all.add(ClientMapper.toDomainModel(client));
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
