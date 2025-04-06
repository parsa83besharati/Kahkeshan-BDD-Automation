package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final String CONFIG_FILE_PATH =
            "E:/java/Kahkeshan-BDD-Automation/Kahkeshan-BDD-Automation/Kahkeshan-BDD-Automation/src/main/resources/config.properties";

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getUsername() {
        return loadProperties().getProperty("username");
    }

    public static String getPassword() {
        return loadProperties().getProperty("password");
    }

    public static String getToken() {
        return loadProperties().getProperty("token");
    }

    public static String getBaseURL() {
        return loadProperties().getProperty("baseurl");
    }

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
