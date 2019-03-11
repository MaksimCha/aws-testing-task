package models;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LambdaModel {

    private String clientRegion = System.getProperty("client.region");
    private String lambdaName = System.getProperty("lambda.name");
    private String lambdaArn = System.getProperty("lambda.arn");
    private String role = System.getProperty("role");
    private String lambdaVersion = System.getProperty("lambda.version");
    private AWSLambda lambdaClient;
    private static final Logger log = LogManager.getLogger(LambdaModel.class);

    public LambdaModel() {
        lambdaClient = AWSLambdaClientBuilder.standard().withRegion(System.getProperty("client.region")).build();
    }

    public void checkParameters() {
        FunctionConfiguration fConf = lambdaClient.listFunctions().getFunctions().get(0);
        assertEquals(fConf.getFunctionName(), System.getProperty("lambda.name"));
        assertEquals(fConf.getFunctionArn(), System.getProperty("lambda.arn"));
        assertTrue(fConf.getRole().contains(System.getProperty("role")));
        assertTrue(fConf.getVersion().contains(System.getProperty("lambda.version")));
        log.info("Check Lambda client parameters done");
    }
}
