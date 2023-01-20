package pl.nbd.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import jakarta.persistence.EntityExistsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TopicExistsException;
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
import pl.nbd.model.domain.Order;
import pl.nbd.model.mgd.AbstractEntityMgd;
import pl.nbd.model.mgd.ItemMgd;
import pl.nbd.model.mgd.LaptopMgd;
import pl.nbd.model.mgd.MonitorMgd;
import pl.nbd.repository.kafka.Producer;
import pl.nbd.repository.kafka.Topics;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static pl.nbd.repository.kafka.Producer.getProducer;

@Slf4j
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
        Topics.createTopic();
        this.collectionName = collectionName;
        initDbConnection();
        Producer.initProducer();
    }

    public void send(T order) {
        try {
            ProducerRecord<UUID, String> record = new ProducerRecord<>(Topics.CLIENT_TOPIC,
                    order.getUniqueId(), new ObjectMapper().writeValueAsString(order) +" " + LocalDateTime.now().toString());
            log.info("record: {}", record.toString());
            Future<RecordMetadata> sent = getProducer().send(record);
            RecordMetadata recordMetadata = sent.get();
        } catch (ExecutionException ee) {
            log.error(String.valueOf(ee.getCause()));
            assertThat(ee.getCause(), is(instanceOf(TopicExistsException.class)));
        } catch (InterruptedException ie) {
            log.error(String.valueOf(ie.getCause()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
        log.info("connected");
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
        log.info("inserted entity with id {}", entity.getUniqueId());
        if(clazz.getSimpleName().equals("OrderMgd")){
            send(entity);
        }
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
        mongoClient.close();
    }
}
