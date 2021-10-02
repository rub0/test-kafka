package nexthink.kafka.consumer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import nexthink.kafka.Entities.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class FileConsumerTest {

  @Test
  public void testReadingFile() throws IOException, URISyntaxException {
    FileConsumer consumer = new FileConsumer();

    List<Person> persons =
      consumer.readPersons(getClass().getClassLoader().getResource("PersonTest.json").toURI().getPath());

    Assertions.assertEquals(persons.size(), 2);
  }

  @Test
  public void testReadingNoInput() throws IOException, URISyntaxException {
    FileConsumer consumer = new FileConsumer();

    List<Person> persons =
      consumer.readPersons(getClass().getClassLoader().getResource("PersonTestNoElements.json").toURI().getPath());

    Assertions.assertEquals(persons.size(), 0);
  }

  @Test
  public void testReadingEmptyFile() throws IOException, URISyntaxException {
    FileConsumer consumer = new FileConsumer();

    try{
      consumer.readPersons(getClass().getClassLoader().getResource("PersonTestEmpty.json").toURI().getPath());
      Assertions.fail("Exception not found");
    } catch (IOException e) {
    }
  }


  @Test
  public void testReadingFileError() {
    FileConsumer consumer = new FileConsumer();

    try{
      consumer.readPersons("missingFile.json");
      Assertions.fail("Exception not found");
    } catch (IOException e) {
    }
  }

}
