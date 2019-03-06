package awsTests;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serviceRepresentations.DynamoRepresentation;
import serviceRepresentations.LambdaRepresentation;
import serviceRepresentations.S3TestRepresentation;

public class EnvironmentTestCase extends TestBase {

    private S3TestRepresentation s3rep;
    private LambdaRepresentation lambdaRep;
    private DynamoRepresentation dynamoRep;

    @BeforeEach
    public void prepareTest() {
        s3rep = new S3TestRepresentation();
        s3rep.prepare();
        lambdaRep = new LambdaRepresentation();
        lambdaRep.prepare();
        dynamoRep = new DynamoRepresentation();
        dynamoRep.prepare();
    }

    @Test
    public void environmentTest() {

        //1. Check s3 parameters
        s3rep.checkParameters();

        //2. Check lambda parameters
        lambdaRep.checkParameters();

        //3. Check dynamoDB parameters
        dynamoRep.checkParameters();
    }
}
