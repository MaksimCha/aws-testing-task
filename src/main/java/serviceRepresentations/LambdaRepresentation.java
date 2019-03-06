package serviceRepresentations;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;

public class LambdaRepresentation {

    private String clientRegion = System.getProperty("client.region");
    private AWSLambda lambdaClient = AWSLambdaClientBuilder.standard().withRegion(clientRegion).build();

    public void prepare() {

    }

    public void checkParameters() {
        System.out.println(lambdaClient.listFunctions());
    }
}
