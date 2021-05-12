package io.github.aveuiller.learning.kafka.raw.consumer;

import io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Implementation of {@link HelloConsumer} using Avro schema and model.
 */
public class AvroConsumer extends AbstractConsumer<AvroHelloMessage> {
    public AvroConsumer(String topic) {
        this(topic, new KafkaConsumer<>(getDefaultProperties(true)));
    }

    public AvroConsumer(String topic, Consumer<Long, AvroHelloMessage> consumer) {
        super(topic, consumer);
    }
}
