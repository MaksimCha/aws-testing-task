package awsTests;


import base.TestBase;
import models.AWSLogsModel;
import models.S3Model;
import org.junit.AfterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

public class UploadFileTestCase extends TestBase {

    private S3Model s3Model;
    private AWSLogsModel awsLogsModel;

    @BeforeEach
    public void prepare() {
        s3Model = new S3Model();
        awsLogsModel = new AWSLogsModel();
    }

    @AfterClass
    public void cleanClass() {
        s3Model.tearDown();
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviders.FileProvider#generateFile")
    public void AWSUploadFileTest(File file) {

        //1. Upload file to s3
        s3Model.uploadFile(file);

        //2. Check lambda
        awsLogsModel.sendRequstLog(file);

        //3. Clean up
        s3Model.deleteLocalFile(file);
    }

    @ParameterizedTest
    @MethodSource(value = "dataProviders.FileProvider#generateFile")
    public void AWSUploadAndDeleteTest(File file) {

        //1. Upload file to s3
        s3Model.uploadFile(file);

        //2. Delete file from s3
        s3Model.deleteFile(file);

        //2. Check lambda
        awsLogsModel.sendRequstLog(file);

        //3. Clean up
        s3Model.deleteLocalFile(file);
    }
}
