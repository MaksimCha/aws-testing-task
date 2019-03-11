package models;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.core.ConditionTimeoutException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AWSLogsModel {

    private AWSLogs awsLogsClient;
    private FilterLogEventsResult result;
    private static final Logger log = LogManager.getLogger(AWSLogsModel.class);


    public void checkLambdaLog(File file) {
        awsLogsClient = AWSLogsClientBuilder
                .standard()
                .withRegion(System.getProperty("client.region"))
                .build();
        FilterLogEventsRequest request = new FilterLogEventsRequest()
                .withLogGroupName("/aws/lambda/FunctionHandler")
                .withFilterPattern("LAMBDA " + file.getName());
        try {
            await()
                    .atMost(30, TimeUnit.SECONDS)
                    .pollInterval(200, TimeUnit.MILLISECONDS)
                    .until(() -> checkArray(request));
            assertTrue(result.getEvents().get(0).getMessage().contains("LAMBDA FileName:" + file.getName()));
            log.info("Log message is correct");
        } catch (ConditionTimeoutException e) {
            log.catching(e);
            log.error("Needed log message does not exist");
        }
    }

    private boolean checkArray(FilterLogEventsRequest request) {
        result = awsLogsClient.filterLogEvents(request);
        return !result.getEvents().isEmpty();
    }

    public void cleanUp() {
        awsLogsClient.shutdown();
    }
}
