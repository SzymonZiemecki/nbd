package pl.nbd.Dao;

import com.datastax.oss.driver.api.mapper.annotations.Dao;
import com.datastax.oss.driver.api.mapper.annotations.Delete;
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider;
import com.datastax.oss.driver.api.mapper.annotations.StatementAttributes;
import pl.nbd.Provider.ItemGetByIdProvider;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.domain.Monitor;

import java.util.UUID;

@Dao
public interface ItemDao {

    @QueryProvider(providerClass = ItemGetByIdProvider.class,
            entityHelpers = {Monitor.class, Laptop.class})
    @StatementAttributes(consistencyLevel = "ONE")
    Item findById(UUID id);

    @QueryProvider(providerClass = ItemGetByIdProvider.class,
            entityHelpers = {Monitor.class, Laptop.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    void create(Item item);

    @Delete
    @StatementAttributes(consistencyLevel = "QUORUM")
    void remove(Item item);

    @QueryProvider(providerClass = ItemGetByIdProvider.class,
            entityHelpers = {Monitor.class, Laptop.class})
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(Item item);
}
