package models;

import com.amazonaws.services.lambda.model.FunctionConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static Singletones.SingletonManager.getLambdaClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LambdaModel {

    private static final Logger log = LogManager.getLogger(LambdaModel.class);

    public void checkParameters() {
        FunctionConfiguration fConf = getLambdaClient().listFunctions().getFunctions().get(0);
        assertEquals(fConf.getFunctionName(), System.getProperty("lambda.name"), "Lambda name is correct: ");
        assertEquals(fConf.getFunctionArn(), System.getProperty("lambda.arn"), "Lambda ARN is correct: ");
        assertTrue(fConf.getRole().contains(System.getProperty("role")), "Role is correct: ");
        assertTrue(fConf.getVersion().contains(System.getProperty("lambda.version")), "Lambda version is correct: ");
        log.info("Check Lambda client parameters done");
    }
}
