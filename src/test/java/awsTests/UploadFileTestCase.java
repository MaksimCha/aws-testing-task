package awsTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

public class UploadFileTestCase {

    @ParameterizedTest
    @MethodSource(value = "common.FileProvider#generateFile")
    public void AWSEnvironmentTest(File file) {
        System.out.println(file);
        System.out.println(file.delete());
    }
}
