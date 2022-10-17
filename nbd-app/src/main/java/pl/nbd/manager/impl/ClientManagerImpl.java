package pl.nbd.manager.impl;

import pl.nbd.manager.ClientManager;
import pl.nbd.model.Client;
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
    public Client updateAddress(Client client){
        return clientRepository.update(client);
    }

    @Override
    public void deleteAddress(Client client) {
        clientRepository.remove(client);
    }

    @Override
    public List<Client> findAllAddresses(){
        return clientRepository.findAll();
    }

    @Override
    public Client findAddressById(Long id){
        return clientRepository.findById(id);
    }
}
