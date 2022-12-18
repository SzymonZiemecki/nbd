package pl.nbd.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.nbd.model.domain.Client;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    void create(Client client);

    @Update
    void update(Client client);

    @Select
    Client select(UUID id);

    @Select
    List<Client> findAll();
    @Delete
    void delete(Client client);

}
