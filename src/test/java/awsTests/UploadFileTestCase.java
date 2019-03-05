package awsTests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

public class UploadFileTestCase {

    @ParameterizedTest
    @MethodSource(value = "common.FileProvider#generateFile")
    public void AWSUploadFileTest(File file) {
        System.out.println(file);
        //1. Upload file to s3

        //2. Check lambda

        //3. Clean up
        System.out.println(file.delete());
    }

    @ParameterizedTest
    @MethodSource(value = "common.FileProvider#generateFile")
    public void AWSUploadAndDeleteTest(File file) {
        System.out.println(file);
        //1. Upload file to s3

        //2. Delete file from s3

        //2. Check lambda

        //3. Clean up
        System.out.println(file.delete());
    }
}
