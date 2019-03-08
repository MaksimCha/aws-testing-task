package awsTests;


import base.TestBase;
import models.AWSLogsModel;
import models.S3Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

public class UploadFileTestCase extends TestBase {

    private S3Model s3Model;
    private AWSLogsModel awsLogsModel;

    @BeforeEach
    public void prepareTest() {
        s3Model = new S3Model();
        s3Model.prepare();
        awsLogsModel = new AWSLogsModel();
    }

    @ParameterizedTest
    @MethodSource(value = "sourceMethods.FileProvider#generateFile")
    public void AWSUploadFileTest(File file) {
        System.out.println("Filepath: " + file);

        //1. Upload file to s3
        s3Model.uploadFile(file);

        //2. Check lambda
        awsLogsModel.checkLambdaLog(file);

        //3. Clean up
        s3Model.cleanUp();
        System.out.println("File was locally deleted: " + file.delete());
    }

    @ParameterizedTest
    @MethodSource(value = "sourceMethods.FileProvider#generateFile")
    public void AWSUploadAndDeleteTest(File file) {
        System.out.println("Filepath: " + file);

        //1. Upload file to s3
        s3Model.uploadFile(file);

        //2. Delete file from s3
        s3Model.deleteFile(file);

        //2. Check lambda
        awsLogsModel.checkLambdaLog(file);

        //3. Clean up
        s3Model.cleanUp();
        System.out.println("File was locally deleted: " + file.delete());
    }
}
