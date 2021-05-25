package io.github.aveuiller.learning.kafka.raw;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.Optional;

public class Main {
    private static final String CHOICE_AVRO = "avro";
    private static final String CHOICE_JSON = "json";

    public static void main(String[] args) throws InterruptedException {
        Optional<Namespace> params = parse(args);
        HelloService service = params.isPresent() && CHOICE_AVRO.equals(params.get().getString("type")) ?
                new AvroService() : new JsonService();

        Thread consumerThread = service.consumeAsync();
        service.produce();

        // Stop in advance
        service.requestStop();
        consumerThread.join();
    }

    private static Optional<Namespace> parse(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("kafka-raw").build()
                .defaultHelp(true)
                .description("Produces and polls HelloMessages in either JSON or Avro.");
        parser.addArgument("-t", "--type")
                .choices(CHOICE_AVRO, CHOICE_JSON).setDefault(CHOICE_JSON)
                .help("Define message format.");
        try {
            return Optional.of(parser.parseArgs(args));
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return Optional.empty();
    }
}
