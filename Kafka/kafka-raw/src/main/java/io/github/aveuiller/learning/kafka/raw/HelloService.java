package io.github.aveuiller.learning.kafka.raw;

public interface HelloService {
    void produce();

    Thread consumeAsync();
}
