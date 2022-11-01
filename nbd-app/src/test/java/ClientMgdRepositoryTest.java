import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.mapper.ClientMapper;
import pl.nbd.model.mgd.*;
import pl.nbd.repository.mongo.ClientMgdRepository;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientMgdRepositoryTest {

    ClientMgdRepository clientMgdRepository;

    @BeforeEach
    public void before(){
        clientMgdRepository= new ClientMgdRepository();
    }


    @Test
    public void addTest(){
        Client client = new Client("test", "test", new Address("test","test","301"), 20.0, true);
        Client client2 = new Client("test", "test", new Address("test","test","30"), 20.0, true);
        clientMgdRepository.add(ClientMapper.toMongoDocument(client));
        clientMgdRepository.add(ClientMapper.toMongoDocument(client2));
        assertEquals(clientMgdRepository.findAll().size(), 2);
    }

    @Test
    public void removeTest(){
        ClientMgd client = new ClientMgd("test", "test", new AddressMgd("test","test","301"), 20.0, true);
        ClientMgd client2 = new ClientMgd("test", "test", new AddressMgd("test","test","30"), 20.0, true);
        clientMgdRepository.add(client);
        clientMgdRepository.add(client2);
        assertEquals(clientMgdRepository.findAll().size(), 2);
        clientMgdRepository.remove(client);
        assertEquals(clientMgdRepository.findAll().size(), 1);
    }

    @Test
    public void updateTest(){
        ClientMgd client = new ClientMgd("test", "test", new AddressMgd("test","test","301"), 20.0, true);
        clientMgdRepository.add(client);
        assertEquals(clientMgdRepository.findById(client.getUniqueId()).get(), client);
        client.setName("changed");
        clientMgdRepository.update(client);
        assertEquals(clientMgdRepository.findById(client.getUniqueId()).get().getName(), "changed");
        client.getAddress().setCity("changed");
        clientMgdRepository.update(client);
        assertEquals(clientMgdRepository.findById(client.getUniqueId()).get().getAddress().getCity(), "changed");
    }

    @AfterEach
    public void after() throws Exception {
        clientMgdRepository.close();
    }
}
