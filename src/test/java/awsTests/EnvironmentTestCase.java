package awsTests;

import base.TestBase;
import models.DynamoModel;
import models.LambdaModel;
import models.S3Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EnvironmentTestCase extends TestBase {

    private S3Model s3Model;
    private LambdaModel lambdaModel;
    private DynamoModel dynamoModel;

    @BeforeEach
    public void prepare() {
        s3Model = new S3Model();
        lambdaModel = new LambdaModel();
        dynamoModel = new DynamoModel();
    }

    @Test
    public void environmentTest() {

        //1. Check s3 parameters
        s3Model.checkParameters();

        //2. Check lambda parameters
        lambdaModel.checkParameters();

        //3. Check dynamoDB parameters
        dynamoModel.checkParameters();
    }
}
