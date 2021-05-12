package io.github.aveuiller.learning.kafka.raw.consumer;

import io.github.aveuiller.learning.kafka.raw.HelloMessage;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Implementation of {@link HelloConsumer} using Json with POJO model.
 */
public class JsonConsumer extends AbstractConsumer<HelloMessage> {
    public JsonConsumer(String topic) {
        this(topic, new KafkaConsumer<>(getDefaultProperties(false)));
    }

    public JsonConsumer(String topic, Consumer<Long, HelloMessage> consumer) {
        super(topic, consumer);
    }
}
