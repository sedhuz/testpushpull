package com.app.properties;

import java.io.FileInputStream;
import java.util.Properties;

public class AppPropertiesHandler {
    static Properties AppProperties;

    static {
        AppProperties = new Properties();
        loadAppConfigProperties();
    }

    private static void loadAppConfigProperties() {
        try {
            FileInputStream appConfigPropertiesFile = new FileInputStream("app.properties");
            AppProperties.load(appConfigPropertiesFile);
        } catch (Exception e) {
            System.err.println(
                    "Error while loading property file! The property file must be located in project's root folder with name - \"app.properties\"");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Properties getAppProperties() {
        return AppProperties;
    }

    public static <T> T getAppProperty(String key, Class<T> type) {
        String value = AppProperties.getProperty(key);
        if (value == null) {
            return null;
        }
        if (type == String.class) {
            return type.cast(value);
        } else if (type == Boolean.class) {
            return type.cast(Boolean.parseBoolean(value));
        } else if (type == Integer.class) {
            return type.cast(Integer.parseInt(value));
        } else if (type == Double.class) {
            return type.cast(Double.parseDouble(value));
        } else if (type == Long.class) {
            return type.cast(Long.parseLong(value));
        }
        throw new IllegalArgumentException("Unsupported type : " + type.getName());
    }
}
