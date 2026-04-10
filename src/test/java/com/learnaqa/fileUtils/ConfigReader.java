package com.learnaqa.fileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;
    static String PATH = "src/main/resources/app.properties";

    private ConfigReader(){
    }

    static {
        try (FileInputStream fis = new FileInputStream(PATH)) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot read config file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
