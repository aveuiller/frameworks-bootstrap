package io.github.aveuiller.learning.kafka.raw.consumer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.aveuiller.learning.kafka.raw.HelloMessage;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HelloMessageDeserializer implements Deserializer<HelloMessage> {
    public HelloMessageDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public HelloMessage deserialize(String topic, byte[] data) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        return gson.fromJson(new String(data, StandardCharsets.UTF_8), HelloMessage.class);
    }

    @Override
    public HelloMessage deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public void close() {

    }
}