package org.reallylastone.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowflake.snowpark_java.DataFrame;
import com.snowflake.snowpark_java.Row;
import com.snowflake.snowpark_java.SaveMode;
import com.snowflake.snowpark_java.Session;
import org.reallylastone.finnhub.FinnhubResponse;
import org.reallylastone.finnhub.MessageHandler;
import org.reallylastone.snowflake.SessionBuilder;
import org.reallylastone.snowflake.TableTypes;

public class WebSocketToSnowflakeSender implements MessageHandler {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Session session = SessionBuilder.create();

    @Override
    public void onMessage(String message) {
        FinnhubResponse response;
        try {
            response = mapper.readValue(message, FinnhubResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        response.getData().forEach(entry -> {
            DataFrame df = session.createDataFrame(new Row[]{Row.create(entry.getP(), entry.getS(), String.valueOf(entry.getT()), entry.getV())},
                    TableTypes.getTradeType());

            df.write().mode(SaveMode.Append).saveAsTable("trade");
        });
    }
}
