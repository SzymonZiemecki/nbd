import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.*;
import pl.nbd.repository.cassandra.ClientRepository;
import pl.nbd.repository.cassandra.ItemRepository;

import java.util.UUID;

public class ItemRepositoryTest {
    public static ItemRepository itemRepository;

    @BeforeAll
    public static void init(){
        itemRepository = new ItemRepository();
        itemRepository.initSession();
        itemRepository.createTable();
    }

    @Test
    public void addTest(){
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 5, "PC","intel", "DLUGI opis", 30.5d, true,"laptop");
        itemRepository.add(item);
        Assertions.assertEquals(itemRepository.findById(item.getUniqueId()).get(), item);
        Item item2 = new Monitor(UUID.randomUUID(),"duzaRozdzielczosc", "duzyPanel", "37cali", 5, "samsung","samsung", "DLUGI opis", 30.5d, true,"monitor");
        itemRepository.add(item2);
        Assertions.assertEquals(itemRepository.findById(item2.getUniqueId()).get(), item2);
    }

    @Test
    public void deleteTest(){
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 5, "PC","intel", "DLUGI opis", 30.5d, true,"laptop");
        itemRepository.add(item);
        Assertions.assertEquals(itemRepository.findById(item.getUniqueId()).get(), item);
        itemRepository.remove(item);
        Assertions.assertThrows(EntityNotFoundException.class, () -> itemRepository.findById(item.getUniqueId()));
    }

    @Test
    public void updateTest(){
        Item item = new Laptop(UUID.randomUUID(),"intel i5", 30l, 30l, 5, "PC","intel", "DLUGI opis", 30.5d, true,"laptop");
        itemRepository.add(item);
        Assertions.assertEquals(itemRepository.findById(item.getUniqueId()).get(), item);
        item.setName("zmieniona");
        itemRepository.update(item);
        Assertions.assertEquals(itemRepository.findById(item.getUniqueId()).get().getName(), "zmieniona");
    }

    @SneakyThrows
    @AfterAll
    public static void ater(){
        itemRepository.close();
    }
}
