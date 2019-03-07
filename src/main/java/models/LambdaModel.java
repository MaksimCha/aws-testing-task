package models;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;

public class LambdaModel {

    private String clientRegion = System.getProperty("client.region");
    private AWSLambda lambdaClient;

    public void prepare() {
        lambdaClient = AWSLambdaClientBuilder.standard().withRegion(clientRegion).build();
    }

    public void checkParameters() {
        System.out.println(lambdaClient.listFunctions().getFunctions().get(0));
    }
}
