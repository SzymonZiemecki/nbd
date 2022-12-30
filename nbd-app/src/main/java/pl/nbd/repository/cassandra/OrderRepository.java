package pl.nbd.repository.cassandra;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.delete.Delete;
import com.datastax.oss.driver.api.querybuilder.insert.Insert;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import com.datastax.oss.driver.api.querybuilder.update.Update;
import pl.nbd.Codec.AddressCodec;
import pl.nbd.Codec.ItemQuantityMapCodec;
import pl.nbd.Codec.OrderCodec;
import pl.nbd.Ids.OrderIds;
import pl.nbd.Mapper.OrderMapperBuilder;
import pl.nbd.model.domain.Order;
import pl.nbd.repository.Repository;

import javax.management.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.currentTimestamp;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class OrderRepository extends AbstractCassandraRepository implements Repository<Order> {

    private static ItemQuantityMapCodec itemQuantityMapCodec = new ItemQuantityMapCodec();
    private static AddressCodec addressCodec = new AddressCodec();
    private static OrderCodec orderCodec = new OrderCodec();

    public void createTable() {
        SimpleStatement createOrdersByClient =
                SchemaBuilder.createTable("shop_app", "orders_by_client")
                        .ifNotExists()
                        .withPartitionKey(OrderIds.UNIQUIE_ID, DataTypes.UUID)
                        .withClusteringColumn(OrderIds.CLIENT_ID, DataTypes.UUID)
                        .withColumn(OrderIds.OBJECT, DataTypes.TEXT)
                        .build();
        session.execute(createOrdersByClient);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        Select select = QueryBuilder.selectFrom(OrderIds.SHOP_APP_NAMESPACE, OrderIds.ORDERS_BY_CLIENT)
                .all()
                .where(Relation.column(OrderIds.UNIQUIE_ID).isEqualTo(literal(id)));
        ResultSet set = session.execute(select.build().setConsistencyLevel(ConsistencyLevel.ONE));
        return Optional.ofNullable(getOrder(set.all().get(0)));
    }

    @Override
    public Order add(Order entity) {
        if(entity.getUniqueId() == null){
            entity.setUniqueId(UUID.randomUUID());
        }
        Insert insert = QueryBuilder.insertInto(OrderIds.ORDERS_BY_CLIENT)
                .value(OrderIds.CLIENT_ID, literal(entity.getClient()))
                .value(OrderIds.UNIQUIE_ID, literal(entity.getUniqueId()))
                .value(OrderIds.OBJECT, literal(orderCodec.format(entity)));
        ResultSet added = session.execute(insert.build().setConsistencyLevel(ConsistencyLevel.QUORUM));
        return entity;
    }

    @Override
    public void remove(Order entity) {
        Delete delete = QueryBuilder.deleteFrom(OrderIds.ORDERS_BY_CLIENT)
                .where(Relation.column(OrderIds.UNIQUIE_ID).isEqualTo(literal(entity.getUniqueId())));
        session.execute(delete.build());
    }

    @Override
    public Order update(Order entity) {
        Insert insert = QueryBuilder.insertInto(OrderIds.ORDERS_BY_CLIENT)
                .value(OrderIds.CLIENT_ID, literal(entity.getClient()))
                .value(OrderIds.UNIQUIE_ID, literal(entity.getUniqueId()))
                .value(OrderIds.OBJECT, literal(orderCodec.format(entity)));
        ResultSet added = session.execute(insert.build().setConsistencyLevel(ConsistencyLevel.QUORUM));
        return entity;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public List<Order> findAll() {
        Select select = QueryBuilder.selectFrom(OrderIds.SHOP_APP_NAMESPACE, OrderIds.ORDERS_BY_CLIENT)
                .all();
        ResultSet set = session.execute(select.build().setConsistencyLevel(ConsistencyLevel.ONE));
        List<Order> orders = set.all().stream()
                .map( this::getOrder)
                .collect(Collectors.toList());
        return orders;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }

    private Order getOrder(Row order){
        return orderCodec.parse(order.getString("object"));
    }
}
