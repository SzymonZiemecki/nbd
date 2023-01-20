package pl.nbd.repository.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.errors.TopicExistsException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Predicates.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Topics {
    public static final String CONSUMER_GROUP_NAME = "ConsumerGroup";
    public static final String CLIENT_TOPIC = "orders";

    public static void createTopic(){
        Properties properties =  new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9192,kafka2:9292,kafka3:9392");
        int partitionsNumber = 3;
        short replicationFactor = 2;
        try (Admin admin = Admin.create(properties)) {
            NewTopic newTopic = new NewTopic(Topics.CLIENT_TOPIC, partitionsNumber, replicationFactor);
            CreateTopicsOptions options = new CreateTopicsOptions()
                    .timeoutMs(10000)
                    .validateOnly(false)
                    .retryOnQuotaViolation(true);
            CreateTopicsResult result = admin.createTopics(List.of(newTopic), options);
            KafkaFuture<Void> futureResult = result.values().get(Topics.CLIENT_TOPIC);
            futureResult.get();
        } catch (ExecutionException ee) {
            System.out.println(ee.getCause());
            Assert.assertThat(ee.getCause(), is(CoreMatchers.instanceOf(TopicExistsException.class)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
