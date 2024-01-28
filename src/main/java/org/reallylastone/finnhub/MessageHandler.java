package org.reallylastone.finnhub;

@FunctionalInterface
public interface MessageHandler {
    void onMessage(String message);
}
