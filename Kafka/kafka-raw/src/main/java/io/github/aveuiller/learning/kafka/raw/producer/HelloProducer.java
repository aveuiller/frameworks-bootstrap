package io.github.aveuiller.learning.kafka.raw.producer;

import org.apache.kafka.clients.producer.Callback;

import java.io.Serializable;

public interface HelloProducer<T extends Serializable> {
    /**
     * Send a HelloMessage entry without callback.
     *
     * @param message message to send.
     */
    void send(long key, T message);

    /**
     * Send a HelloMessage on the HELLO_TOPIC topic, executing the callback on sent.
     *
     * @param message  HelloMessage to send.
     * @param callback {@link Callback} to execute on message sent.
     */
    void send(long key, T message, Callback callback);

    /**
     * Returns the topic in which this producer will send the data.
     *
     * @return The generated topic.
     */
    String getTopic();
}
