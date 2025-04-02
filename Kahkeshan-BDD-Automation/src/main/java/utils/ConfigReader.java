package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    // Load properties from the config file
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    // Get the username from the config file
    public static String getUsername() {
        return loadProperties().getProperty("username");
    }

    // Get the password from the config file
    public static String getPassword() {
        return loadProperties().getProperty("password");
    }

    // Get the token from the config file
    public static String getToken() {
        return loadProperties().getProperty("token");
    }

    // Save the token in the config file
    public static void saveToken(String token) {
        Properties properties = loadProperties();
        properties.setProperty("token", token);
        try (FileOutputStream fileOutputStream = new FileOutputStream(CONFIG_FILE_PATH)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
