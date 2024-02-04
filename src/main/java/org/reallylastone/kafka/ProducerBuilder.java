package org.reallylastone.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.reallylastone.finnhub.Entry;

import java.util.Properties;

import static org.reallylastone.util.Utils.getEnv;

public class ProducerBuilder {
    private ProducerBuilder() {
        throw new AssertionError();
    }

    public static KafkaProducer<String, Entry> create()  {
        System.out.println("Creating Kafka producer...");
        Properties config = new Properties();
        //getEnv("KAFKA_BOOTSTRAP_SERVERS")
        config.put("bootstrap.servers", getEnv("KAFKA_BOOTSTRAP_SERVERS"));
        config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        config.put("value.serializer", "org.reallylastone.kafka.EntrySerde");

        return new KafkaProducer<>(config);
    }
}
