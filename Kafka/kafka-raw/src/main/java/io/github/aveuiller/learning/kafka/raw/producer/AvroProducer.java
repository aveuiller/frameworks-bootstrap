package io.github.aveuiller.learning.kafka.raw.producer;

import io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;

/**
 * Simple wrapper around {@link Producer} sending messsages to HELLO_TOPIC.
 * Either {@link io.github.aveuiller.learning.kafka.raw.HelloMessage} or {@link AvroHelloMessage}.
 * <p>
 * See the {@link KafkaProducer} implementation for more info:
 * https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/clients/producer/KafkaProducer.java
 */
public class AvroProducer extends AbstractProducer<AvroHelloMessage> {
    public AvroProducer() {
        this(new KafkaProducer<>(getDefaultProperties(true)));
    }

    public AvroProducer(Producer<Long, AvroHelloMessage> producer) {
        super(producer);
    }

    public String getTopic() {
        return String.format("%s_%s", HELLO_TOPIC, "avro");
    }
}
