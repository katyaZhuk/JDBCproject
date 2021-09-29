package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesHelper {
    static Properties props = new Properties();

    public static void loadConnectionFile () throws IOException {
        props.load(new FileInputStream("src/main/resources/connection.properties"));
    }

    public static void loadCreds () throws IOException {
        props.load(new FileInputStream("src/main/resources/credentials.properties"));
    }

    public static String getHost() throws IOException {
        loadConnectionFile();
        return props.getProperty("host");
    }

    public static String getDBName() throws IOException {
        loadConnectionFile();
        return props.getProperty("DBname");
    }

    public static String getURL() throws IOException {
        loadConnectionFile();
        return props.getProperty("url");
    }

    public static String getUser() throws IOException {
        loadConnectionFile();
        return props.getProperty("user");
    }

    public static String getPassword() throws IOException {
        loadConnectionFile();
        return props.getProperty("password");
    }

    public static String getID() throws IOException {
        loadCreds();
        return props.getProperty("id");
    }

    public static String getFirstName() throws IOException {
        loadCreds();
        return props.getProperty("first_name");
    }

    public static String getLastName() throws IOException {
        loadCreds();
        return props.getProperty("last_name");
    }

    public static String getLastCountry() throws IOException {
        loadCreds();
        return props.getProperty("last_country");
    }

    public static String getExpectedCountry() throws IOException {
        loadCreds();
        return props.getProperty("expected_country");
    }

    public static String getExpectedLastName() throws IOException {
        loadCreds();
        return props.getProperty("expected_last_name");
    }

    public static String getJoinCountry() throws IOException {
        loadCreds();
        return props.getProperty("join_country");
    }

}
