package io.github.aveuiller.learning.kafka.raw;

import io.github.aveuiller.learning.kafka.raw.consumer.AvroConsumer;
import io.github.aveuiller.learning.kafka.raw.generated.AvroHelloMessage;
import io.github.aveuiller.learning.kafka.raw.producer.AvroProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service Producing and consuming Avro compatible messages trough the generated POJO {@link AvroHelloMessage}.
 */
public class AvroService extends AbstractService {
    private static final Logger LOG = LoggerFactory.getLogger("AvroService");
    private final AvroConsumer consumer;
    private final AvroProducer producer;

    public AvroService() {
        producer = new AvroProducer();
        consumer = new AvroConsumer(producer.getTopic());
    }

    public void produce() {
        final AvroHelloMessage message = new AvroHelloMessage(1L, "this is a message", 2.4f, 1);
        final AvroHelloMessage secondMessage = new AvroHelloMessage(1L, "this is another message", 2.4f, 2);
        final AvroHelloMessage messageAnotherUser = new AvroHelloMessage(2L, "this is another message", 2.6f, 1);

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
        ConsumerRecords<Long, AvroHelloMessage> poll = retrieveAll ? consumer.fetchAll() : consumer.poll();
        LOG.info("=== Polled data");
        for (ConsumerRecord<Long, AvroHelloMessage> record : poll) {
            LOG.info("---");
            LOG.info(String.format("Fetched record %s", record.toString()));
            AvroHelloMessage value = record.value();
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
