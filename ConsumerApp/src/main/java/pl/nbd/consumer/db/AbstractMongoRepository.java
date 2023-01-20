package pl.nbd.consumer.db;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.MonitorMgd;

import java.util.List;

public abstract class AbstractMongoRepository implements AutoCloseable {

    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private MongoCredential credential = MongoCredential.createCredential("nbd", "admin", "nbdpassword".toCharArray());
    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());

    private MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;

    public void initDbConnection() {
        ClassModel<ItemMgd> itemMgd = ClassModel.builder(ItemMgd.class).enableDiscriminator(true).build();
        ClassModel<LaptopMgd> laptopMgd = ClassModel.builder(LaptopMgd.class).enableDiscriminator(true).build();
        ClassModel<MonitorMgd> monitorMgd = ClassModel.builder(MonitorMgd.class).enableDiscriminator(true).build();
        PojoCodecProvider itemMgdProvider = PojoCodecProvider.builder()
                .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
                .register(itemMgd, laptopMgd).build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(MongoClientSettings.getDefaultCodecRegistry(),
                                pojoCodecRegistry,
                                itemMgdProvider)
                )).build();
        mongoClient = MongoClients.create(settings);
        mongoDatabase = mongoClient.getDatabase("test");
        System.out.println("connected");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

}
