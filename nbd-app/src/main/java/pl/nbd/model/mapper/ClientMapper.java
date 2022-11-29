package pl.nbd.model.mapper;

import pl.nbd.model.domain.Client;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.redis.ClientJson;

public class ClientMapper {

    public static ClientMgd toMongoDocument(Client client){
        return ClientMgd.builder()
                .uniqueId(client.getUniqueId())
                .name(client.getName())
                .surname(client.getSurname())
                .accountBalance(client.getAccountBalance())
                .isSuspened(client.isSuspened())
                .address(AddressMapper.toMongoDocument(client.getAddress()))
                .build();
    }

    public static ClientJson toRedisDocument(Client client){
        return ClientJson.builder()
                .uniqueId(client.getUniqueId())
                .name(client.getName())
                .surname(client.getSurname())
                .accountBalance(client.getAccountBalance())
                .isSuspened(client.isSuspened())
                .address(AddressMapper.toRedisDocument(client.getAddress()))
                .build();
    }

    public static Client toDomainModel(ClientMgd client){
        return Client.builder()
                .uniqueId(client.getUniqueId())
                .name(client.getName())
                .surname(client.getSurname())
                .accountBalance(client.getAccountBalance())
                .isSuspened(client.isSuspened())
                .address(AddressMapper.toDomainModel(client.getAddress()))
                .build();
    }

    public static Client toDomainModel(ClientJson client){
        return Client.builder()
                .uniqueId(client.getUniqueId())
                .name(client.getName())
                .surname(client.getSurname())
                .accountBalance(client.getAccountBalance())
                .isSuspened(client.isSuspened())
                .address(AddressMapper.toDomainModel(client.getAddress()))
                .build();
    }
}
