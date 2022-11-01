import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.mgd.AddressMgd;
import pl.nbd.model.mgd.ClientMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.repository.mongo.ClientMgdRepository;
import pl.nbd.repository.mongo.ItemMgdRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRepositoryMgdTest {
    ItemMgdRepository itemMgdRepository;

    @BeforeEach
    public void before(){
        itemMgdRepository= new ItemMgdRepository();
    }


    @Test
    public void addTest(){
        ItemMgd item = new LaptopMgd(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        ItemMgd item2 = new LaptopMgd(20l, "raz2", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        itemMgdRepository.add(item);
        itemMgdRepository.add(item2);
        assertEquals(itemMgdRepository.findAll().size(), 2);
        assertEquals(itemMgdRepository.findById(item.getUniqueId()).get(), item);
    }

    @Test
    public void removeTest(){
        ItemMgd item = new LaptopMgd(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        ItemMgd item2 = new LaptopMgd(20l, "raz2", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        itemMgdRepository.add(item);
        itemMgdRepository.add(item2);
        assertEquals(itemMgdRepository.findAll().size(), 2);
        itemMgdRepository.remove(item);
        assertEquals(itemMgdRepository.findAll().size(), 1);
    }

    @Test
    public void updateTest(){
        ItemMgd item = new LaptopMgd(20l, "raz", "dwa", "trzy", 20.0, "raz", 20l, 20l);
        itemMgdRepository.add(item);
        assertEquals(itemMgdRepository.findById(item.getUniqueId()).get(), item);
        item.setName("changed");
        itemMgdRepository.update(item);
        assertEquals(itemMgdRepository.findById(item.getUniqueId()).get().getName(), "changed");
    }

    @AfterEach
    public void after() throws Exception {
        itemMgdRepository.close();
    }
}
