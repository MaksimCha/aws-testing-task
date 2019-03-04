package awsTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

public class EnvironmentTest {

    @Test
    @ParameterizedTest
    @MethodSource(value = "common.FileProvider#generateFile")
    public void AWSEnvironmentTest(File file) {
        System.out.println(file);
    }
}
