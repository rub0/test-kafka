/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package nexthink.kafka;

import java.io.IOException;

import nexthink.kafka.consumer.FileConsumer;
import nexthink.kafka.producer.NexthinkKafkaProducer;
import org.apache.commons.cli.*;


public class App {

    public CommandLine parseArgs(String[] args) {
        Options options = new Options();

        Option topic = new Option("t", "topic", true, "topic where you store the people");
        topic.setRequired(true);
        options.addOption(topic);

        Option file = new Option("f", "file", true, "input file");
        file.setRequired(true);
        options.addOption(file);

        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            return null;
        }

    }

    public void startApp(String args[]) throws IOException {

        CommandLine input = parseArgs(args);

        NexthinkKafkaProducer producer = new NexthinkKafkaProducer(input.getOptionValue("topic"));

        new FileConsumer().readPersons(input.getOptionValue("file")).stream().forEach(p -> producer.pushPerson(p));

        producer.close();
    }

    public static void main(String[] args) throws IOException {
        new App().startApp(args);
    }
}
