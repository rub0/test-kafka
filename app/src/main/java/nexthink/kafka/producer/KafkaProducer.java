package nexthink.kafka.producer;

import nexthink.kafka.Entities.Person;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.KafkaFuture;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducer {

    Producer<String, Person> producer;

    String topic;

    Properties properties;


    private void createTopic(String topic){
        try (Admin admin = Admin.create(properties)) {
            int partitions = 1;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(topic, partitions, replicationFactor);

            CreateTopicsResult result = admin.createTopics(Collections.singleton(newTopic));

            // get the async result for the new topic creation
            KafkaFuture<Void> future = result.values()
                    .get(topic);

            // call get() to block until topic creation has completed or failed
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public KafkaProducer (String topic) throws IOException {

        properties = loadConfig();
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonSerializer");

    }

    public void pushPerson(Person p){
        producer.send(new ProducerRecord<String, Person>(topic, "key", p), new Callback() {
            @Override
            public void onCompletion(RecordMetadata m, Exception e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    System.out.printf("Produced record to topic %s partition [%d] @ offset %d%n", m.topic(), m.partition(), m.offset());
                }
            }
        });
    }

    public Properties loadConfig() throws IOException {
        final Properties cfg = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kafka.properties")) {
            cfg.load(inputStream);
        }
        return cfg;
    }
}
