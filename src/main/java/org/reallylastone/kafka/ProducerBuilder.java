package org.reallylastone.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.reallylastone.finnhub.Entry;

import java.util.Properties;

public class ProducerBuilder {
    private ProducerBuilder() {
        throw new AssertionError();
    }

    public static KafkaProducer<String, Entry> create()  {
        Properties config = new Properties();
        config.put("bootstrap.servers", "localhost:29092");
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.reallylastone.kafka.EntrySerde");

        return new KafkaProducer<>(config);
    }
}
