package models;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LambdaModel {

    private String clientRegion = System.getProperty("client.region");
    private String lambdaName = System.getProperty("lambda.name");
    private String lambdaArn = System.getProperty("lambda.arn");
    private String role = System.getProperty("role");
    private String lambdaVersion = System.getProperty("lambda.version");
    private AWSLambda lambdaClient;

    public void prepare() {
        lambdaClient = AWSLambdaClientBuilder.standard().withRegion(clientRegion).build();
    }

    public void checkParameters() {
        FunctionConfiguration fConf = lambdaClient.listFunctions().getFunctions().get(0);
        assertEquals(fConf.getFunctionName(), lambdaName);
        assertEquals(fConf.getFunctionArn(), lambdaArn);
        assertTrue(fConf.getRole().contains(role));
        assertTrue(fConf.getVersion().contains(lambdaVersion));
    }
}
