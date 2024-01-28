package org.reallylastone.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.reallylastone.finnhub.Entry;
import org.reallylastone.finnhub.FinnhubResponse;
import org.reallylastone.finnhub.MessageHandler;
import org.reallylastone.kafka.ProducerBuilder;

public class WebSocketToKafkaSender implements MessageHandler {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final KafkaProducer<String, Entry> producer = ProducerBuilder.create();


    @Override
    public void onMessage(String message) {
        FinnhubResponse response;
        try {
            response = mapper.readValue(message, FinnhubResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.getData().forEach(entry -> producer.send(new ProducerRecord<>("trade", entry)));
    }
}
