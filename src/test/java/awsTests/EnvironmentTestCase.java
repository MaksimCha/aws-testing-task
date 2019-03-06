package awsTests;

import base.TestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serviceRepresentations.S3TestRepresentation;

public class EnvironmentTestCase extends TestBase {

    private S3TestRepresentation s3rep;

    @BeforeEach
    public void prepareTest() {
        s3rep = new S3TestRepresentation();
        s3rep.prepareS3();
    }

    @Test
    public void environmentTest() {

        //1. Check s3 parameters
        s3rep.checkParameters();

        //2. Check lambda parameters

        //3. Check dynamoDB parameters
    }
}
