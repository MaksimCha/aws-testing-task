package base;

import Singletones.SingletonManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.setProperty;

public class TestBase {

    @BeforeAll
    public static void setProps() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream("aws.properties")) {
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setProperty("bucket.name", prop.getProperty("bucket.name"));
        setProperty("client.region", prop.getProperty("client.region"));
        setProperty("lambda.name", prop.getProperty("lambda.name"));
        setProperty("owner.id", prop.getProperty("owner.id"));
        setProperty("table.name", prop.getProperty("table.name"));
        setProperty("lambda.arn", prop.getProperty("lambda.arn"));
        setProperty("role", prop.getProperty("role"));
        setProperty("lambda.version", prop.getProperty("lambda.version"));
    }

    @AfterAll
    public static void tearDown() {
        SingletonManager.tearDown();
    }
}
