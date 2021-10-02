package nexthink.kafka.producer;

import java.io.IOException;

import nexthink.kafka.Entities.Person;
import nexthink.kafka.consumer.FileConsumer;
import org.junit.jupiter.api.Test;


public class ProducerTest {

  @Test
  public void testNoExceptionThrown() throws IOException {
    FileConsumer consumer = new FileConsumer();

    NexthinkKafkaProducer producer = new NexthinkKafkaProducer("test-topic");

    producer.pushPerson(new Person("Ruben", "id", "email@domain.com"));
    producer.close();
  }

}
