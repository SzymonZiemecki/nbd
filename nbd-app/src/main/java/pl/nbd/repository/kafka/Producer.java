package pl.nbd.repository.kafka;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Predicates.instanceOf;
import static net.bytebuddy.matcher.ElementMatchers.is;

public class Producer {
    @Getter
    @Setter
    private static KafkaProducer producer;

    public static KafkaProducer getProducer() {
        return producer;
    }

    public static void initProducer() {
        Properties producerConfig = new Properties();
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class.getName());
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerConfig.put(ProducerConfig.CLIENT_ID_CONFIG, "local");
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192, kafka2:9292, kafka3:9392");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        producer = new KafkaProducer(producerConfig);
    }
}
