package io.github.aveuiller.learning.kafka.raw;

import io.github.aveuiller.learning.kafka.raw.consumer.JsonConsumer;
import io.github.aveuiller.learning.kafka.raw.producer.JsonProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service Producing and consuming JSON compatible messages trough the manually defined POJO {@link HelloMessage}.
 */
public class JsonService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger("AvroService");
    private final JsonConsumer consumer;
    private final JsonProducer producer;

    public JsonService() {
        producer = new JsonProducer();
        consumer = new JsonConsumer(producer.getTopic());
    }

    public void produce() {
        final HelloMessage message = new HelloMessage(1L, "this is a message", 2.4f, 1);
        final HelloMessage secondMessage = new HelloMessage(1L, "this is another message", 2.4f, 2);
        final HelloMessage messageAnotherUser = new HelloMessage(2L, "this is another message", 2.6f, 1);

        producer.send(message.getUserId(), message);
        producer.send(message.getUserId(), secondMessage);
        producer.send(messageAnotherUser.getUserId(), messageAnotherUser, (metadata, exception) -> {
            if (null != exception) {
                LOG.error(String.format("Error on message %s: %s", messageAnotherUser.getUserId(), exception));
            } else {
                LOG.info(String.format("Sent message %s", metadata));
            }
        });
        producer.close();
    }

    @Override
    protected void consume(boolean retrieveAll) {
        ConsumerRecords<Long, HelloMessage> poll = retrieveAll ? consumer.fetchAll() : consumer.poll();

        LOG.info("=== Polled data");
        for (ConsumerRecord<Long, HelloMessage> record : poll) {
            LOG.info("---");
            LOG.info(String.format("Fetched record %s", record.toString()));
            HelloMessage value = record.value();
            LOG.info(String.format("Data: %s - %s", value.getUserId(), value.getMessage()));
            LOG.info(String.format("Headers: %s", record.headers()));
            LOG.info("---");
        }
        LOG.info("=== End data");
    }

    @Override
    protected void stopConsumer() {
        consumer.close();
    }
}
