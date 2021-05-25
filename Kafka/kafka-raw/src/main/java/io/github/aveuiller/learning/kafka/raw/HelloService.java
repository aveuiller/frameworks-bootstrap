package io.github.aveuiller.learning.kafka.raw;

public interface HelloService {
    /**
     * Produce and publish a few test messages.
     */
    void produce();

    /**
     * Start regularly polling Kafka brokers for new messages.
     *
     * @return The started {@link Thread} for polling.
     */
    Thread consumeAsync();

    /**
     * Request a stop on the consumer polling loop.
     */
    void requestStop();
}
