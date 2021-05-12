package io.github.aveuiller.learning.kafka.raw.producer;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GsonSerializer<T extends Serializable> implements Serializer<T> {
    public GsonSerializer(){}

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

   @Override
    public byte[] serialize(String topic, T data) {
       Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
       return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
    }
 
    @Override
    public void close() {
    } 
}