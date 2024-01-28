package org.reallylastone.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.reallylastone.finnhub.Entry;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EntrySerde implements Serializer<Entry>, Deserializer<Entry> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Entry data) {
        try {
            return mapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        Serializer.super.close();
    }

    @Override
    public Entry deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(new String(data, StandardCharsets.UTF_8), Entry.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
