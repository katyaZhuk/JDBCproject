package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    static Properties props = new Properties();

    public static String getHost() throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
        return props.getProperty("host");
    }

    public static String getDBName() throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
        return props.getProperty("DBname");
    }

    public static String getURL() throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
        return props.getProperty("url");
    }

    public static String getUser() throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
        return props.getProperty("user");
    }

    public static String getPassword() throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
        return props.getProperty("password");
    }

}
