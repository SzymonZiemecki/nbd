package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.model.Client;
import pl.nbd.model.Order;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ClientRepository;

import java.util.List;

public class ClientManagerImpl implements ClientManager {
    private ClientRepository clientRepository;
    private AddressRepository addressRepository;

    public ClientManagerImpl(ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository= addressRepository;
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
        return clientRepository.findAll();
    }

    @Override
    public Client findClientsById(Long id){
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findByClientsByName(String name) {
        return clientRepository.findClientByName(name);
    }

    @Override
    public List<Order> findClientOrders(Long id) {
        return clientRepository.findClientOrders(clientRepository.findById(id));
    }
}
