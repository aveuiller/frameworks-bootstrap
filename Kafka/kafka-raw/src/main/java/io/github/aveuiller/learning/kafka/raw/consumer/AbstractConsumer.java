package io.github.aveuiller.learning.kafka.raw.consumer;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Simple wrapper around {@link Consumer} fetching messages in the given topic.
 * Either {@link io.github.aveuiller.learning.kafka.raw.HelloMessage} or {@link io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage}.
 * <p>
 * See the {@link KafkaConsumer} implementation for more info:
 * https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/clients/consumer/KafkaConsumer.java
 */
abstract class AbstractConsumer<T extends Serializable> implements HelloConsumer<T> {
    private static final Logger LOG = LoggerFactory.getLogger("AbstractConsumer");
    public static final Duration TIMEOUT = Duration.ofSeconds(5);

    protected final String topic;
    protected final Consumer<Long, T> consumer;

    public AbstractConsumer(String topic, Consumer<Long, T> consumer) {
        this.topic = topic;
        this.consumer = consumer;
        this.subscribe();
    }

    protected static Properties getDefaultProperties(boolean useAvro) {
        Properties defaultProperties = new Properties();
        defaultProperties.put("bootstrap.servers", "localhost:29092");
        defaultProperties.put("group.id", "HelloConsumer");
        defaultProperties.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        if (useAvro) {
            defaultProperties.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
            defaultProperties.put("schema.registry.url", "http://localhost:8081");
            // Configure Avro deserializer to convert the received data to a SpecificRecord (i.e. HelloMessage)
            // instead of a GenericRecord (i.e. schema + array of deserialized data).
            defaultProperties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        } else {
            defaultProperties.put("value.deserializer", "io.github.aveuiller.learning.kafka.raw.consumer.HelloMessageDeserializer");
        }
        return defaultProperties;
    }

    public ConsumerRecords<Long, T> poll() {
        return consumer.poll(TIMEOUT);
    }

    public ConsumerRecords<Long, T> fetchAll() {
        // Ensure that the assignments are filled before resetting offsets
        consumer.poll(TIMEOUT);

        String topics = consumer.assignment().stream().map(TopicPartition::topic).collect(Collectors.joining(", "));
        LOG.info(String.format("Calling reset on all subscriptions: %s", topics));

        consumer.seekToBeginning(Collections.emptyList());
        return consumer.poll(TIMEOUT);
    }

    public void subscribe() {
        consumer.subscribe(Pattern.compile(topic));
    }

    public void close() {
        consumer.close(TIMEOUT);
    }
}
