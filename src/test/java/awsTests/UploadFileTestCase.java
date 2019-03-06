package awsTests;

import base.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import stepDefinitions.S3TestRepresentation;

import java.io.File;

public class UploadFileTestCase extends TestBase {

    private S3TestRepresentation s3rep;

    @BeforeEach
    public void prepareTest() {
        s3rep = new S3TestRepresentation();
        s3rep.prepareS3();
    }

    @ParameterizedTest
    @MethodSource(value = "common.FileProvider#generateFile")
    public void AWSUploadFileTest(File file) {
        System.out.println(file);
        //1. Upload file to s3
        S3TestRepresentation s3rep = new S3TestRepresentation();
        s3rep.prepareS3("aws");
        s3rep.uploadObject(file);
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
