package org.reallylastone;

import org.reallylastone.finnhub.FinnhubWebSocketClient;
import org.reallylastone.sender.WebSocketToKafkaSender;
import org.reallylastone.sender.WebSocketToSnowflakeSender;

import java.util.List;

import static org.reallylastone.util.Utils.getEnv;

public class App {

    private static final List<String> events = List.of("AAPL", "AMZN", "BINANCE:BTCUSDT", "IC MARKETS:1");

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting application...");
        // add --add-opens=java.base/java.nio=ALL-UNNAMED to VM options
        System.out.println("Creating FinnhubWebSocketClient...");
        FinnhubWebSocketClient client = new FinnhubWebSocketClient(getEnv("FINNHUB_TOKEN"), events);

        // connection lost timeout = -1 because the library expects to receive a 'pong' message but it never happens
        client.setConnectionLostTimeout(-1);

        System.out.println("Setting up WebSocketToKafkaSender...");
        client.addMessageHandler(new WebSocketToKafkaSender());

        System.out.println("Setting up WebSocketToSnowflakeSender...");
        client.addMessageHandler(new WebSocketToSnowflakeSender());

        System.out.println("Connecting to Finnhub service...");
        client.connect();

        Thread.currentThread().join();
    }
}
