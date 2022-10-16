package pl.nbd.manager;

import pl.nbd.model.Address;
import pl.nbd.model.Client;
import pl.nbd.repository.AddressRepository;
import pl.nbd.repository.ClientRepository;

import java.util.List;

public class ClientManager {
    private ClientRepository clientRepository;
    private AddressRepository addressRepository;

    public ClientManager(ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository= addressRepository;
    }

    public Client addClient(Client client) {
        return clientRepository.add(client);
    }

    public Client updateAddress(Client client){
        return clientRepository.update(client);
    }

    public void deleteAddress(Client client) {
        clientRepository.remove(client);
    }

    public List<Client> findAllAddresses(){
        return clientRepository.findAll();
    }

    public Client findAddressById(Long id){
        return clientRepository.findById(id);
    }
}
