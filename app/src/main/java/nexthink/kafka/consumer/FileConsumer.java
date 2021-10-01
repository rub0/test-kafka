package nexthink.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nexthink.kafka.Entities.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileConsumer {

    ObjectMapper mapper = new ObjectMapper();

    public List<Person> readPersons(String filePath) throws IOException {
        return mapper.readValue(new File(filePath), new TypeReference<List<Person>>() {});
    }
}
