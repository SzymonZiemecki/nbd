package pl.nbd.repository.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataType;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import pl.nbd.Codec.AddressCodec;
import pl.nbd.Dao.ItemDao;
import pl.nbd.Ids.ItemIds;
import pl.nbd.Mapper.ItemMapper;
import pl.nbd.Mapper.ItemMapperBuilder;
import pl.nbd.model.domain.Item;
import pl.nbd.repository.Repository;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.nbd.Ids.ItemIds.SHOP_APP_NAMESPACE;
import static pl.nbd.Ids.ItemIds.UNIQUIE_ID;

public class ItemRepository implements Repository<Item> {
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
    }

    public void createTable(){
        SimpleStatement createTableIfNotExists =
                SchemaBuilder.createTable("shop_app","items")
                        .ifNotExists()
                        .withPartitionKey(UNIQUIE_ID, DataTypes.UUID)
                        .withColumn(ItemIds.NAME, DataTypes.ASCII)
                        .withColumn(ItemIds.PRODUCER, DataTypes.ASCII)
                        .withColumn(ItemIds.AVAILABLE_AMOUNT, DataTypes.BIGINT)
                        .withColumn(ItemIds.DESCRIPTION, DataTypes.ASCII)
                        .withColumn(ItemIds.PRICE, DataTypes.DOUBLE)
                        .withColumn(ItemIds.AVAILABLE, DataTypes.BOOLEAN)
                        .withColumn(ItemIds.DISCRIMINATOR, DataTypes.ASCII)
                        .withColumn(ItemIds.CPU, DataTypes.ASCII)
                        .withColumn(ItemIds.RAM_AMOUNT, DataTypes.BIGINT)
                        .withColumn(ItemIds.MEMORY_AMOUNT, DataTypes.BIGINT)
                        .withColumn(ItemIds.RESOLUTION, DataTypes.ASCII)
                        .withColumn(ItemIds.PANEL, DataTypes.ASCII)
                        .withColumn(ItemIds.DIAGONAL, DataTypes.ASCII)
                        .build();
        session.execute(createTableIfNotExists);
    }

    @Override
    public Optional<Item> findById(UUID id) {
        ItemMapper itemMapper = new ItemMapperBuilder(session).build();
        ItemDao itemDao = itemMapper.itemDao("shop_app", "items");
        return Optional.ofNullable(itemDao.findById(id));
    }

    @Override
    public Item add(Item entity) {
        ItemMapper itemMapper = new ItemMapperBuilder(session).build();
        ItemDao itemDao = itemMapper.itemDao("shop_app", "items");
        itemDao.create(entity);
        return entity;
    }

    @Override
    public void remove(Item entity) {
        ItemMapper itemMapper = new ItemMapperBuilder(session).build();
        ItemDao itemDao = itemMapper.itemDao("shop_app", "items");
        itemDao.remove(entity);
    }

    @Override
    public Item update(Item entity) {
        ItemMapper itemMapper = new ItemMapperBuilder(session).build();
        ItemDao itemDao = itemMapper.itemDao("shop_app", "items");
        itemDao.update(entity);
        return entity;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public List<Item> findAll() {
        return null;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
