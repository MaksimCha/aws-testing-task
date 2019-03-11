package base;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.System.setProperties;

public class TestBase {

    @BeforeAll
    public static void setProps() {
        Properties prop = new Properties();
        try (FileInputStream stream = new FileInputStream("aws.properties")){
            prop.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setProperties(prop);
    }
}
