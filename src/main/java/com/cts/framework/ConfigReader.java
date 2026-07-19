package com.cts.framework;

public class ConfigReader {

    private ConfigReader() {}

    public static long getSeedFromEnv() {
        String seedStr = System.getProperty("cucumber.seed");
        if (seedStr != null) {
            try {
                return Long.parseLong(seedStr);
            } catch (NumberFormatException e) {
                return System.currentTimeMillis();
            }
        }
        return System.currentTimeMillis();
    }
}
