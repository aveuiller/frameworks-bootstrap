package io.github.aveuiller.learning.kafka.raw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractService implements HelloService {
    private static final Logger LOG = LoggerFactory.getLogger("AbstractService");
    private static final int SECONDS_POLLING = 20;
    private static final int MILLISECONDS_WAIT_BETWEEN_POLL = 1000;
    protected final AtomicBoolean shouldStop = new AtomicBoolean(false);

    public Thread consumeAsync() {
        Runnable runnable = () -> {
            OffsetDateTime currentTime = OffsetDateTime.now();
            OffsetDateTime endTime = currentTime.plus(SECONDS_POLLING, ChronoUnit.SECONDS);

            // Reset the offset to 0 on first call.
            this.consume(true);
            while (currentTime.compareTo(endTime) < 0 && !shouldStop.get()) {
                try {
                    Thread.sleep(MILLISECONDS_WAIT_BETWEEN_POLL);
                } catch (InterruptedException e) {
                    LOG.error(String.format("Error on thread sleep %s", e));
                } finally {
                    currentTime = OffsetDateTime.now();
                }

                this.consume(false);
            }
            stopConsumer();
        };

        Thread consumerThread = new Thread(runnable);
        consumerThread.start();
        return consumerThread;
    }

    protected abstract void consume(boolean retrieveAll);

    protected abstract void stopConsumer();

    public void requestStop() {
        shouldStop.set(true);
    }
}
