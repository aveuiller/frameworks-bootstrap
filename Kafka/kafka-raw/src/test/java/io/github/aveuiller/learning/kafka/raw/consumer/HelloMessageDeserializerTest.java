package io.github.aveuiller.learning.kafka.raw.consumer;


import io.github.aveuiller.learning.kafka.raw.HelloMessage;
import io.github.aveuiller.learning.kafka.raw.producer.GsonSerializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HelloMessageDeserializerTest {
    HelloMessage originalMessage;
    byte[] helloMessageData;
    String topic = "anytopic";

    @Before
    public void setUp() throws Exception {
        originalMessage = new HelloMessage(1, "test", 2, 3);
        helloMessageData = new GsonSerializer<HelloMessage>().serialize(topic, originalMessage);
    }

    @Test
    public void testDeserialization() {
        HelloMessageDeserializer helloMessageDeserializer = new HelloMessageDeserializer();

        HelloMessage message = helloMessageDeserializer.deserialize(topic, helloMessageData);

        Assert.assertNotNull(message);
        Assert.assertEquals(originalMessage.getMessage(), message.getMessage());
    }
}
