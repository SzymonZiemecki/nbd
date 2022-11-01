package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Order;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.repository.mongo.ClientMgdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientManagerImpl implements ClientManager {
    private ClientMgdRepository clientRepository;

    public ClientManagerImpl() {
        this.clientRepository = new ClientMgdRepository();
    }

    @Override
    public Client addClient(Client client) {
        return ClientMapper.toDomainModel(clientRepository.add(ClientMapper.toMongoDocument(client)));
    }

    @Override
    public Client updateClient(Client client){
        return ClientMapper.toDomainModel(clientRepository.update(ClientMapper.toMongoDocument(client)));
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.remove(ClientMapper.toMongoDocument(client));
    }

    @Override
    public List<Client> findAllClients(){
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().stream().forEach(client -> clients.add(ClientMapper.toDomainModel(client)));
        return clients;
    }

    @Override
    public Optional<Client> findClientsById(UUID id){
        return Optional.of(ClientMapper.toDomainModel(clientRepository.findById(id).get()));
    }

/*    @Override
    public List<Client> findByClientsByName(String name) {
        return clientRepository.findClientByName(name);
    }

    @Override
    public List<Order> findClientOrders(UUID id) {
        return clientRepository.findClientOrders(clientRepository.findById(id));
    }*/
}
