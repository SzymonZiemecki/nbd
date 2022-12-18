import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.repository.cassandra.ClientRepository;

import java.util.UUID;

public class ClientRepositoryTest {

    public static ClientRepository clientRepository;

    @BeforeAll
    public static void init(){
        clientRepository = new ClientRepository();
        clientRepository.initSession();
        clientRepository.createTable();
    }

    @Test
    public void addTest(){
            Client client = Client.builder()
                    .name("Szymon")
                    .surname("Ziemecki")
                    .accountBalance(30d)
                    .address(new Address("test","test","test"))
                    .suspened(false)
                    .uniqueId(UUID.randomUUID())
                    .build();
            clientRepository.add(client);
            Assertions.assertEquals(clientRepository.findById(client.getUniqueId()).get(), client);
    }

    @Test
    public void deleteTest(){
            Client client = Client.builder()
                    .name("Szymon")
                    .surname("Ziemecki")
                    .accountBalance(30d)
                    .address(new Address("test","test","test"))
                    .suspened(false)
                    .uniqueId(UUID.randomUUID())
                    .build();
            clientRepository.add(client);
            Assertions.assertEquals(clientRepository.findById(client.getUniqueId()).get(), client);
            clientRepository.remove(client);
            Assertions.assertFalse(clientRepository.findById(client.getUniqueId()).isPresent());
    }

    @Test
    public void updateTest(){
            Client client = Client.builder()
                    .name("Szymon")
                    .surname("Ziemecki")
                    .accountBalance(30d)
                    .address(new Address("test","test","test"))
                    .suspened(false)
                    .uniqueId(UUID.randomUUID())
                    .build();
            clientRepository.add(client);
            Assertions.assertEquals(clientRepository.findById(client.getUniqueId()).get(), client);
            client.setAccountBalance(50d);
            clientRepository.update(client);
            Assertions.assertEquals(clientRepository.findById(client.getUniqueId()).get().getAccountBalance(), 50d);
    }

/*    @Test
    public void findAllTest(){
        try(ClientRepository clientRepository = new ClientRepository()){
            clientRepository.initSession();
            clientRepository.createTable();
            Client client = Client.builder()
                    .name("Szymon")
                    .surname("Ziemecki")
                    .accountBalance(30d)
                    .address(new Address("test","test","test"))
                    .suspened(false)
                    .uniqueId(UUID.randomUUID())
                    .build();
            Assertions.assertFalse(clientRepository.findAll().isEmpty());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

    @SneakyThrows
    @AfterAll
    public static void ater(){
        clientRepository.close();
    }
}
