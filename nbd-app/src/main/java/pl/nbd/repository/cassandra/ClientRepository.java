package pl.nbd.repository.cassandra;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import pl.nbd.Codec.AddressCodec;
import pl.nbd.Dao.ClientDao;
import pl.nbd.Mapper.ClientMapper;
import pl.nbd.Mapper.ClientMapperBuilder;
import pl.nbd.model.domain.Client;
import pl.nbd.repository.Repository;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements Repository<Client> {

    private static final CqlIdentifier SHOP_APP_NAMESPACE = CqlIdentifier.fromCql("shop_app");
    private static final CqlIdentifier UNIQUIE_ID = CqlIdentifier.fromCql("unique_id");
    private static final CqlIdentifier NAME = CqlIdentifier.fromCql("name");
    private static final CqlIdentifier SURNAME = CqlIdentifier.fromCql("surname");
    private static final CqlIdentifier ADDRESS = CqlIdentifier.fromCql("address");
    private static final CqlIdentifier ACCOUNT_BALANCE = CqlIdentifier.fromCql("account_balance");
    private static final CqlIdentifier IS_SUSPENDED = CqlIdentifier.fromCql("is_suspended");
    private static CqlSession session;

    public void initSession(){
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .build();
        session.execute(SchemaBuilder.createKeyspace(SHOP_APP_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true)
                .build());
        MutableCodecRegistry registry = (MutableCodecRegistry) session.getContext().getCodecRegistry();
        registry.register(new AddressCodec());
    }

    public void createTable(){
        SimpleStatement createTableIfNotExists =
                SchemaBuilder.createTable("shop_app","clients")
                        .ifNotExists()
                        .withPartitionKey(UNIQUIE_ID, DataTypes.UUID)
                        .withColumn(NAME, DataTypes.ASCII)
                        .withColumn(SURNAME, DataTypes.ASCII)
                        .withColumn(ADDRESS, DataTypes.ASCII)
                        .withColumn(ACCOUNT_BALANCE, DataTypes.DOUBLE)
                        .withColumn(IS_SUSPENDED, DataTypes.BOOLEAN)
                        .build();
        session.execute(createTableIfNotExists);
    }

    @Override
    public Optional<Client> findById(UUID id) {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao("shop_app", "clients");
        return Optional.ofNullable(clientDao.select(id));
    }

    @Override
    public Client add(Client entity) {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao("shop_app", "clients");
        clientDao.create(entity);
        return entity;
    }

    @Override
    public void remove(Client entity) {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao("shop_app", "clients");
        clientDao.delete(entity);
    }

    @Override
    public Client update(Client entity) {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao("shop_app", "clients");
        clientDao.update(entity);
        return entity;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public List<Client> findAll() {
        ClientMapper clientMapper = new ClientMapperBuilder(session).build();
        ClientDao clientDao = clientMapper.clientDao("shop_app", "clients");
        return clientDao.findAll();
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
