import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.Address;
import pl.nbd.model.domain.Client;
import pl.nbd.model.domain.Order;

import java.util.UUID;

public class OrderRepositoryTest {

    @Test
    public void orderTest(){
        Order order = new Order(new Client("iime", "test",new Address("test","Test","test"),120d,false), )
    }

}
