package pl.nbd.manager.impl;

import jakarta.persistence.EntityNotFoundException;
import pl.nbd.manager.ClientManager;
import pl.nbd.model.domain.Client;
import pl.nbd.repository.cassandra.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientManagerImpl implements ClientManager {
    private ClientRepository clientRepository;

    @Override
    public Client addClient(Client client) {
        return clientRepository.add(client);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.update(client);
    }

    @Override
    public void deleteClient(Client client) {
        clientRepository.remove(client);
    }

    @Override
    public List<Client> findAllClients() {
        return null;
    }

    @Override
    public Client findClientsById(UUID id) {
        return clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("entity not found"));
    }
}