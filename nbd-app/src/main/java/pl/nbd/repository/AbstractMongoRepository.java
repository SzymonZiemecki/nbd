package pl.nbd.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import jakarta.persistence.EntityExistsException;
import org.apache.commons.lang3.NotImplementedException;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import pl.nbd.model.mgd.AbstractEntityMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.MonitorMgd;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AbstractMongoRepository<T extends AbstractEntityMgd> implements MongoRepository<T> {

    private ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
    private MongoCredential credential = MongoCredential.createCredential("nbd", "admin", "nbdpassword".toCharArray());
    private CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
    private MongoClient mongoClient;
    private MongoDatabase mongoDb;

    protected Class<T> clazz;

    protected String collectionName;

    public AbstractMongoRepository(Class<T> clazz, String collectionName) {
        this.clazz = clazz;
        this.collectionName = collectionName;
        initDbConnection();
    }

    private void initDbConnection() {
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
        mongoDb = mongoClient.getDatabase("test");
        System.out.println("connected");
    }


    @Override
    public Optional<T> findById(UUID id) {
        MongoCollection<T> collection = mongoDb.getCollection(collectionName, clazz);
        Bson filter = Filters.eq("_id",id);
        Optional<T> found = Optional.of(collection.find(filter).first());
        return found;
    }

    @Override
    public T add(T entity) {
        MongoCollection<T> collection = mongoDb.getCollection(collectionName, clazz);
        if(entity.getUniqueId() == null){
            entity.setUniqueId(UUID.randomUUID());
        }
        else {
            throw new EntityExistsException("Entity with provided UUID alredy exists in databse");
        }
        collection.insertOne(entity);
        return entity;
    }

    @Override
    public void remove(T entity) {
        MongoCollection<T> collection = mongoDb.getCollection(collectionName, clazz);
        Bson filter = Filters.eq("_id", entity.getUniqueId());
        collection.deleteOne(filter);
    }

    @Override
    public T update(T updatedEntity) {
        MongoCollection<T> collection = mongoDb.getCollection(collectionName, clazz);
        Bson filter = Filters.eq("_id", updatedEntity.getUniqueId());
        collection.findOneAndReplace(filter, updatedEntity);
        return updatedEntity;
    }

    @Override
    public long size() {
        return findAll().size();
    }

    @Override
    public List<T> findAll() {
        List<T> all = new ArrayList<>();
        MongoCollection<T> collection = mongoDb.getCollection(collectionName, clazz);
        MongoCursor<T> cursor = collection.find(clazz).iterator();
        while(cursor.hasNext()){
            all.add(cursor.next());
        }
        return all;
    }

    @Override
    public void close() throws Exception {
        mongoClient.getDatabase("test").drop();
        mongoClient.close();
    }
}
