package pl.nbd.manager;

import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientManager {
    Client addClient(Client client);

    Client updateClient(Client client);

    void deleteClient(Client client);

    List<Client> findAllClients();

    Optional<Client> findClientsById(UUID id);

/*    List<Client> findByClientsByName(String name);

    List<Order> findClientOrders(UUID id);*/
}
