package io.github.aveuiller.learning.kafka.raw.producer;

import io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthenticationException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.errors.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Properties;

/**
 * Simple wrapper around {@link org.apache.kafka.clients.producer.Producer} sending messsages to HELLO_TOPIC.
 * Either {@link io.github.aveuiller.learning.kafka.raw.HelloMessage} or {@link AvroHelloMessage}.
 * <p>
 * See the {@link KafkaProducer} implementation for more info:
 * https://github.com/apache/kafka/blob/trunk/clients/src/main/java/org/apache/kafka/clients/producer/KafkaProducer.java
 */
abstract class AbstractProducer<T extends Serializable> implements HelloProducer<T> {
    private static final Logger LOG = LoggerFactory.getLogger("SimpleProducer");
    protected static final String HELLO_TOPIC = "hello_topic";

    protected final Producer<Long, T> producer;

    public AbstractProducer(Producer<Long, T> producer) {
        this.producer = producer;
    }

    public static Properties getDefaultProperties(boolean useAvro) {
        Properties defaultProperties = new Properties();
        defaultProperties.put("bootstrap.servers", "localhost:29092");
        defaultProperties.put("acks", "all");
        defaultProperties.put("retries", 0);
        defaultProperties.put("linger.ms", 1);
        defaultProperties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        if (useAvro) {
            defaultProperties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
            defaultProperties.put("schema.registry.url", "http://localhost:8081");
        } else {
            defaultProperties.put("value.serializer", "io.github.aveuiller.learning.kafka.raw.producer.GsonSerializer");
        }
        return defaultProperties;
    }

    public void send(long key, T message) {
        send(key, message, null);
    }

    public void send(long key, T message, Callback callback) {
        ProducerRecord<Long, T> record = new ProducerRecord<>(getTopic(), key, message);

        try {
            producer.send(record, callback);
        } catch (AuthenticationException e) {
            LOG.error(String.format("Unable to authenticate on Kafka broker: %s", e));
        } catch (AuthorizationException e) {
            LOG.error(String.format("Could not write on topic: %s - %s", HELLO_TOPIC, e));
        } catch (IllegalStateException e) {
            LOG.error(String.format("Issue with client state: %s", e));
        } catch (InterruptException e) {
            LOG.error(String.format("Thread interrupted: %s", e));
        } catch (SerializationException e) {
            LOG.error(String.format("Unable to serialize data: %s", e));
        } catch (KafkaException e) {
            LOG.error(String.format("Something went wrong on Kafka broker: %s", e));
        }
    }

    public void close() {
        producer.close();
    }
}
