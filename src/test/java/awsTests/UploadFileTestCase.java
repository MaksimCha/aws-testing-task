package awsTests;


import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import serviceRepresentations.S3TestRepresentation;

import java.io.File;

public class UploadFileTestCase extends TestBase {

    private S3TestRepresentation s3rep;

    @BeforeEach
    public void prepareTest() {
        s3rep = new S3TestRepresentation();
        s3rep.prepareS3();
    }

    @ParameterizedTest
    @MethodSource(value = "sourceMethods.FileProvider#generateFile")
    public void AWSUploadFileTest(File file) {
        System.out.println("Filepath: " + file);

        //1. Upload file to s3
        s3rep.uploadFile(file);

        //2. Check lambda

        //3. Clean up
        System.out.println("File was locally deleted: " + file.delete());
    }

    @ParameterizedTest
    @MethodSource(value = "sourceMethods.FileProvider#generateFile")
    public void AWSUploadAndDeleteTest(File file) {
        System.out.println("Filepath: " + file);

        //1. Upload file to s3
        s3rep.uploadFile(file);

        //2. Delete file from s3
        s3rep.deleteFile(file);

        //2. Check lambda

        //3. Clean up
        s3rep.cleanUp();
        System.out.println("File was locally deleted: " + file.delete());
    }
}
