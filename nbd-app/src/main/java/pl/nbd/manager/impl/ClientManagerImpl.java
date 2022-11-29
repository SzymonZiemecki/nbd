package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Order;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.repository.base.ClientRepository;
import pl.nbd.repository.mongo.ClientMgdRepository;
import pl.nbd.repository.redis.ClientRedisRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientManagerImpl implements ClientManager {
    private ClientRepository clientRepository;

    public ClientManagerImpl() {
        this.clientRepository = new ClientRepository(new ClientMgdRepository(), new ClientRedisRepository());
    }

    @Override
    public Client addClient(Client client) {
        return clientRepository.add(client);
    }

    @Override
    public Client updateClient(Client client){
        return clientRepository.update(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.remove(client);
    }

    @Override
    public List<Client> findAllClients(){
        List<Client> clients = new ArrayList<>();
        clientRepository.findAll().stream().forEach(client -> clients.add(client));
        return clients;
    }

    @Override
    public Optional<Client> findClientsById(UUID id){
        return Optional.of(clientRepository.findById(id).get());
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
