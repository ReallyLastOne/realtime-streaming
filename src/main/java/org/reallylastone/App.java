package org.reallylastone;

import org.reallylastone.runnable.WebSocketToSnowflakeRunnable;

public class App {

    public static void main(String[] args) throws InterruptedException {
        // add --add-opens=java.base/java.nio=ALL-UNNAMED to VM options

        new Thread(new WebSocketToSnowflakeRunnable()).start();

        Thread.currentThread().join();
    }
}
