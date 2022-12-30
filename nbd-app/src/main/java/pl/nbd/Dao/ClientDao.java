package pl.nbd.Dao;

import com.datastax.oss.driver.api.mapper.annotations.*;
import pl.nbd.model.domain.Client;

import java.util.List;
import java.util.UUID;

@Dao
public interface ClientDao {
    @Insert
    @StatementAttributes(consistencyLevel = "QUORUM")
    void create(Client client);

    @Update
    @StatementAttributes(consistencyLevel = "QUORUM")
    void update(Client client);

    @Select
    @StatementAttributes(consistencyLevel = "ONE")
    Client select(UUID id);

    @Select
    @StatementAttributes(consistencyLevel = "ONE")
    List<Client> findAll();
    @Delete
    @StatementAttributes(consistencyLevel = "QUORUM")
    void delete(Client client);

}
