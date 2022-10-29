package pl.nbd.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public class AbstractMongoRepository<T> implements MongoRepository<T>{

    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private MongoCredential credential = MongoCredential.createCredential("nbd","admin", "nbdpassword".toCharArray());
    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
    private MongoClient mongoClient;
    private MongoDatabase mongoDb;

    protected Class<T> clazz;

    public AbstractMongoRepository(Class<T> clazz) {
        this.clazz = clazz;
        initDbConnection();
    }

    private void initDbConnection(){
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyConnectionString(connectionString)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .codecRegistry(CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(MongoClientSettings.getDefaultCodecRegistry(),
                                pojoCodecRegistry)
                )).build();
        mongoClient = MongoClients.create(settings);
        mongoDb = mongoClient.getDatabase("test");
        System.out.println("connected");
    }


    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public T add(T entity) {
        return null;
    }

    @Override
    public void remove(T entity) {

    }

    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public long size() {
        return 0;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
