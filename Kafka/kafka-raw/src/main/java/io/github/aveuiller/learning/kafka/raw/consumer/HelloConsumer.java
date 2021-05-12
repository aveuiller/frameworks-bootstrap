package io.github.aveuiller.learning.kafka.raw.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.io.Serializable;

public interface HelloConsumer<T extends Serializable> {

    /**
     * Subscribe to the Hello topic.
     */
    void subscribe();

    /**
     * Fetch all logs from assigned topics' partitions from offset 0.
     *
     * @return The list of records.
     */
    ConsumerRecords<Long, T> fetchAll();

    /**
     * Fetch new records from assigned topics' partitions.
     *
     * @return
     */
    ConsumerRecords<Long, T> poll();

    /**
     * Close the underlying consumer.
     */
    void close();
}
