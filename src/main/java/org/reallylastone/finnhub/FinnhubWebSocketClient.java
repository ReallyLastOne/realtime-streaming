package org.reallylastone.finnhub;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;


public class FinnhubWebSocketClient extends WebSocketClient {
    public static final String MESSAGE_TEMPLATE = "{\"type\":\"subscribe\",\"symbol\":\"%s\"}";
    private final List<String> subscribedEvents;
    private MessageHandler messageHandler = System.out::println;

    public FinnhubWebSocketClient(String token, List<String> subscribedEvents) {
        super(URI.create("wss://ws.finnhub.io?token=" + token));
        this.subscribedEvents = subscribedEvents;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        subscribedEvents.forEach(symbol -> this.send(String.format(MESSAGE_TEMPLATE, symbol)));
    }

    @Override
    public void onMessage(String s) {
        if (s.contains("\"type\":\"ping\"")) return;

        try {
            messageHandler.onMessage(s);
        } catch (Exception e) {
            System.out.println(s);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Connection closed: " + s);
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
}