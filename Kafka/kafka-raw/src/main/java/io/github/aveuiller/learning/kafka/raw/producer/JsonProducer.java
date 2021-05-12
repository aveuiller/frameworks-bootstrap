package io.github.aveuiller.learning.kafka.raw.producer;

import io.github.aveuiller.learning.kafka.raw.HelloMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;


public class JsonProducer extends AbstractProducer<HelloMessage> {
    public JsonProducer() {
        this(new KafkaProducer<>(AbstractProducer.getDefaultProperties(false)));
    }

    public JsonProducer(Producer<Long, HelloMessage> producer) {
        super(producer);
    }

    public String getTopic() {
        return String.format("%s_%s", HELLO_TOPIC, "json");
    }
}
