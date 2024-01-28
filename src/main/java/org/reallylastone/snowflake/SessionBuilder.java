package org.reallylastone.snowflake;


import java.util.HashMap;
import java.util.Map;

import com.snowflake.snowpark_java.Session;

import static org.reallylastone.util.Utils.getEnv;

public class SessionBuilder {
    private SessionBuilder() {
        throw new AssertionError();
    }

    public static Session create() {
        Map<String, String> configMap = new HashMap<>();

        configMap.put("URL", "https://" + getEnv("SNOWFLAKE_ACCOUNT") + ".snowflakecomputing.com");
        configMap.put("USER", getEnv("SNOWFLAKE_USER"));
        configMap.put("PASSWORD", getEnv("SNOWFLAKE_PASSWORD"));
        configMap.put("DB", getEnv("SNOWFLAKE_DATABASE"));
        configMap.put("ROLE", getEnv("SNOWFLAKE_ROLE"));
        configMap.put("SCHEMA", getEnv("SNOWFLAKE_SCHEMA"));
        configMap.put("WAREHOUSE", getEnv("SNOWFLAKE_WAREHOUSE"));

        return Session.builder().configs(configMap).create();
    }
}