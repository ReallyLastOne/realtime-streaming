package org.reallylastone.util;

public class Utils {
    private Utils() {
        throw new AssertionError();
    }

    public static String getEnv(String key) {
        try {
            return System.getenv(key);
        } catch (Exception e) {
            throw new IllegalStateException(String.format("No environmental variable: %s provided", key), e);
        }
    }
}
