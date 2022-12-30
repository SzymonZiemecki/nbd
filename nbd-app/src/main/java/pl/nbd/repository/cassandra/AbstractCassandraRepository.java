package pl.nbd.repository.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.type.codec.registry.MutableCodecRegistry;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import pl.nbd.Codec.AddressCodec;
import pl.nbd.Codec.ItemQuantityMapCodec;
import pl.nbd.Codec.OrderCodec;

import java.net.InetSocketAddress;

import static pl.nbd.Ids.ClientIds.SHOP_APP_NAMESPACE;

public abstract class AbstractCassandraRepository {

    protected static CqlSession session;

    protected MutableCodecRegistry registry;

    public void initSession(){
        session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("localhost", 9042))
                .addContactPoint(new InetSocketAddress("localhost", 9043))
                .withKeyspace("shop_app")
                .withLocalDatacenter("dc1")
                .withAuthCredentials("cassandra", "cassandra")
                .build();
        session.execute(SchemaBuilder.createKeyspace(SHOP_APP_NAMESPACE)
                .ifNotExists()
                .withSimpleStrategy(2)
                .withDurableWrites(true)
                .build());
        registry =(MutableCodecRegistry) session.getContext().getCodecRegistry();
        registry.register(new AddressCodec());
        registry.register(new OrderCodec());
    }
}
