package pl.nbd.manager;

import pl.nbd.model.Client;

import java.util.List;

public interface ClientManager {
    public Client addClient(Client client);

    public Client updateAddress(Client client);

    public void deleteAddress(Client client);

    public List<Client> findAllAddresses();

    public Client findAddressById(Long id);
}
