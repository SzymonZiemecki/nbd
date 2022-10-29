/*
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.Address;
import pl.nbd.repository.AbstractMongoRepository;

import java.util.UUID;

public class mongoTest {

    AbstractMongoRepository  mongoRepository;

    @BeforeEach
    public void before(){
        mongoRepository = new AbstractMongoRepository();
    }


    @Test
    public void test(){
        Address address = new Address(UUID.randomUUID(), "test", "test", "test");
        mongoRepository.add(address);
    }

    @AfterEach
    public void after() throws Exception {
        mongoRepository.close();
    }
}
*/
