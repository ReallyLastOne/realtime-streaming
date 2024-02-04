package org.reallylastone.snowflake;


import com.snowflake.snowpark_java.Session;

import java.util.HashMap;
import java.util.Map;

import static org.reallylastone.util.Utils.getEnv;

public class SessionBuilder {
    private SessionBuilder() {
        throw new AssertionError();
    }

    public static Session create() {
        System.out.println("Creating Snowflake session...");
        Map<String, String> configMap = new HashMap<>();

        configMap.put("URL", "https://" + getEnv("SNOWFLAKE_ACCOUNT") + ".snowflakecomputing.com");
        configMap.put("USER", getEnv("SNOWFLAKE_USER"));
        configMap.put("PRIVATE_KEY_FILE", getEnv("SNOWFLAKE_PRIVATE_KEY_FILE"));
        configMap.put("DB", getEnv("SNOWFLAKE_DATABASE"));
        configMap.put("ROLE", getEnv("SNOWFLAKE_ROLE"));
        configMap.put("SCHEMA", getEnv("SNOWFLAKE_SCHEMA"));
        configMap.put("WAREHOUSE", getEnv("SNOWFLAKE_WAREHOUSE"));

        return Session.builder().configs(configMap).create();
    }
}