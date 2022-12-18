package pl.nbd.Provider;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.mapper.MapperContext;
import com.datastax.oss.driver.api.mapper.entity.EntityHelper;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import jakarta.persistence.EntityNotFoundException;
import pl.nbd.Ids.ItemIds;
import pl.nbd.model.domain.Item;
import pl.nbd.model.domain.Laptop;
import pl.nbd.model.domain.Monitor;

import java.util.Optional;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

public class ItemGetByIdProvider {

    private final CqlSession session;
    private EntityHelper<Monitor> monitorEntityHelper;
    private EntityHelper<Laptop> laptopEntityHelper;

    public ItemGetByIdProvider(MapperContext ctx, EntityHelper<Monitor> monitorEntityHelper, EntityHelper<Laptop> laptopEntityHelper) {
        this.session = ctx.getSession();
        this.monitorEntityHelper = monitorEntityHelper;
        this.laptopEntityHelper = laptopEntityHelper;
    }

    public void create(Item item){
        session.execute(
                switch (item.getDiscriminator()) {
                    case "laptop" -> {
                        Laptop laptop = (Laptop) item;
                        yield session.prepare(laptopEntityHelper.insert().build())
                                .bind()
                                .setUuid(ItemIds.UNIQUIE_ID, laptop.getUniqueId())
                                .setLong(ItemIds.AVAILABLE_AMOUNT, laptop.getAvailableAmount())
                                .setString(ItemIds.NAME, laptop.getName())
                                .setString(ItemIds.PRODUCER, laptop.getProducer())
                                .setString(ItemIds.DESCRIPTION, laptop.getDescription())
                                .setDouble(ItemIds.PRICE, laptop.getPrice())
                                .setBoolean(ItemIds.AVAILABLE, laptop.getAvailable())
                                .setString(ItemIds.CPU, laptop.getCpu())
                                .setLong(ItemIds.RAM_AMOUNT, laptop.getRamAmount())
                                .setLong(ItemIds.MEMORY_AMOUNT, laptop.getMemoryAmount())
                                .setString(ItemIds.DISCRIMINATOR, laptop.getDiscriminator());
                    }
                    case "monitor" -> {
                        Monitor monitor = (Monitor) item;
                        yield session.prepare(monitorEntityHelper.insert().build())
                                .bind()
                                .setUuid(ItemIds.UNIQUIE_ID, monitor.getUniqueId())
                                .setLong(ItemIds.AVAILABLE_AMOUNT, monitor.getAvailableAmount())
                                .setString(ItemIds.NAME, monitor.getName())
                                .setString(ItemIds.PRODUCER, monitor.getProducer())
                                .setString(ItemIds.DESCRIPTION, monitor.getDescription())
                                .setString(ItemIds.RESOLUTION, monitor.getResolution())
                                .setString(ItemIds.PANEL, monitor.getPanel())
                                .setString(ItemIds.DIAGONAL, monitor.getDiagonal())
                                .setDouble(ItemIds.PRICE, monitor.getPrice())
                                .setBoolean(ItemIds.AVAILABLE, monitor.getAvailable())
                                .setString(ItemIds.DISCRIMINATOR, monitor.getDiscriminator());
                    }
                    default -> throw new IllegalArgumentException();
                }
        );
    }

    public void update(Item item){
        session.execute(
                switch (item.getDiscriminator()) {
                    case "laptop" -> {
                        Laptop laptop = (Laptop) item;
                        yield session.prepare(laptopEntityHelper.updateByPrimaryKey().build())
                                .bind()
                                .setUuid(ItemIds.UNIQUIE_ID, laptop.getUniqueId())
                                .setLong(ItemIds.AVAILABLE_AMOUNT, laptop.getAvailableAmount())
                                .setString(ItemIds.NAME, laptop.getName())
                                .setString(ItemIds.PRODUCER, laptop.getProducer())
                                .setString(ItemIds.DESCRIPTION, laptop.getDescription())
                                .setDouble(ItemIds.PRICE, laptop.getPrice())
                                .setBoolean(ItemIds.AVAILABLE, laptop.getAvailable())
                                .setString(ItemIds.CPU, laptop.getCpu())
                                .setLong(ItemIds.RAM_AMOUNT, laptop.getRamAmount())
                                .setLong(ItemIds.MEMORY_AMOUNT, laptop.getMemoryAmount())
                                .setString(ItemIds.DISCRIMINATOR, laptop.getDiscriminator());

                    }
                    case "monitor" -> {
                        Monitor monitor = (Monitor) item;
                        yield session.prepare(monitorEntityHelper.updateByPrimaryKey().build())
                                .bind()
                                .setUuid(ItemIds.UNIQUIE_ID, monitor.getUniqueId())
                                .setLong(ItemIds.AVAILABLE_AMOUNT, monitor.getAvailableAmount())
                                .setString(ItemIds.NAME, monitor.getName())
                                .setString(ItemIds.PRODUCER, monitor.getProducer())
                                .setString(ItemIds.DESCRIPTION, monitor.getDescription())
                                .setString(ItemIds.RESOLUTION, monitor.getResolution())
                                .setString(ItemIds.PANEL, monitor.getPanel())
                                .setString(ItemIds.DIAGONAL, monitor.getDiagonal())
                                .setDouble(ItemIds.PRICE, monitor.getPrice())
                                .setBoolean(ItemIds.AVAILABLE, monitor.getAvailable())
                                .setString(ItemIds.DISCRIMINATOR, monitor.getDiscriminator());
                    }
                    default -> throw new IllegalArgumentException();
                }
        );
    }

    public Item findById(UUID id){
        Select selectItem = QueryBuilder
                .selectFrom(CqlIdentifier.fromCql("shop_app"), CqlIdentifier.fromCql("items"))
                .all()
                .where(Relation.column(CqlIdentifier.fromCql("unique_id")).isEqualTo(literal(id)));
        Optional<Row> row = Optional.ofNullable(session.execute(selectItem.build()).one());
        Row resolvedRow = row.orElseThrow(() -> new EntityNotFoundException("entity not found"));
        String discriminator = resolvedRow.getString(ItemIds.DISCRIMINATOR);
            return switch (discriminator) {
                case "laptop" -> getLaptop(resolvedRow);
                case "monitor" -> getMonitor(resolvedRow);
                default -> throw new IllegalArgumentException();
            };
    }

    private Item getMonitor(Row row) {
        return new Monitor(
                row.getUuid(ItemIds.UNIQUIE_ID),
                row.getString(ItemIds.RESOLUTION),
                row.getString(ItemIds.PANEL),
                row.getString(ItemIds.DIAGONAL),
                row.getLong(ItemIds.AVAILABLE_AMOUNT),
                row.getString(ItemIds.NAME),
                row.getString(ItemIds.PRODUCER),
                row.getString(ItemIds.DESCRIPTION),
                row.getDouble(ItemIds.PRICE),
                row.getBoolean(ItemIds.AVAILABLE),
                row.getString(ItemIds.DISCRIMINATOR)
        );
    }

    private Item getLaptop(Row row) {
        return new Laptop(
                row.getUuid(ItemIds.UNIQUIE_ID),
                row.getString(ItemIds.CPU),
                row.getLong(ItemIds.RAM_AMOUNT),
                row.getLong(ItemIds.MEMORY_AMOUNT),
                row.getLong(ItemIds.AVAILABLE_AMOUNT),
                row.getString(ItemIds.NAME),
                row.getString(ItemIds.PRODUCER),
                row.getString(ItemIds.DESCRIPTION),
                row.getDouble(ItemIds.PRICE),
                row.getBoolean(ItemIds.AVAILABLE),
                row.getString(ItemIds.DISCRIMINATOR)
        );
    }
}
