package org.reallylastone.runnable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowflake.snowpark_java.DataFrame;
import com.snowflake.snowpark_java.Row;
import com.snowflake.snowpark_java.SaveMode;
import com.snowflake.snowpark_java.Session;
import org.reallylastone.finnhub.FinnhubResponse;
import org.reallylastone.finnhub.FinnhubWebSocketClient;
import org.reallylastone.snowflake.SessionBuilder;
import org.reallylastone.snowflake.TableTypes;

import java.util.List;

import static org.reallylastone.util.Utils.getEnv;

public class WebSocketToSnowflakeRunnable implements Runnable {
    private static final List<String> events = List.of("AAPL", "AMZN", "BINANCE:BTCUSDT", "IC MARKETS:1");
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Session session;

    public WebSocketToSnowflakeRunnable() {
        this.session = SessionBuilder.create();
    }

    @Override
    public void run() {
        FinnhubWebSocketClient client = new FinnhubWebSocketClient(getEnv("FINNHUB_TOKEN"), events);

        // connection lost timeout = -1 because the library expects to receive a 'pong' message but it never happens
        client.setConnectionLostTimeout(-1);
        client.setMessageHandler(message -> {
            FinnhubResponse response = mapper.readValue(message, FinnhubResponse.class);
            response.getData().forEach(entry -> {
                DataFrame df = session.createDataFrame(new Row[]{Row.create(entry.getP(), entry.getS(), String.valueOf(entry.getT()), entry.getV())},
                        TableTypes.getTradeType());

                df.write().mode(SaveMode.Append).saveAsTable("trade");
            });
        });

        client.connect();
    }
}
