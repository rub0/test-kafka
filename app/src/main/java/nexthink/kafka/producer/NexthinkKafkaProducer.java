package nexthink.kafka.producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import nexthink.kafka.Entities.Person;
import org.apache.kafka.clients.producer.*;


public class NexthinkKafkaProducer {

  Producer<String, Person> producer;

  String topic;

  Properties properties;

  public NexthinkKafkaProducer(String topic) throws IOException {

    this.topic = topic;

    properties = loadConfig();

    producer = new KafkaProducer(properties);
  }

  public void pushPerson(Person p) {
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

  public void close() {
    producer.close();
  }

  private Properties loadConfig() throws IOException {
    final Properties cfg = new Properties();
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kafka.properties")) {
      cfg.load(inputStream);
    }
    return cfg;
  }
}
