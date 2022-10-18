package pl.nbd.manager;

import pl.nbd.model.Client;
import pl.nbd.model.Order;

import java.util.List;

public interface ClientManager {
    Client addClient(Client client);

    Client updateClient(Client client);

    void deleteClient(Client client);

    List<Client> findAllClients();

    Client findClientsById(Long id);

    List<Client> findByClientsByName(String name);

    List<Order> findClientOrders(Long id);
}
