package models;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AWSLogsModel {

    private String clientRegion = System.getProperty("client.region");
    private AWSLogs awsLogsClient;
    private FilterLogEventsResult result;

    public void checkLambdaLog(File file) {
        awsLogsClient = AWSLogsClientBuilder.standard().withRegion(clientRegion).build();
        FilterLogEventsRequest request = new FilterLogEventsRequest().withLogGroupName("/aws/lambda/FunctionHandler").withFilterPattern("LAMBDA " + file.getName());
        await()
                .atMost(30, TimeUnit.SECONDS)
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .until(() -> checkArray(request));
        assertTrue(result.getEvents().get(0).getMessage().contains("LAMBDA FileName:" + file.getName()));
    }

    private boolean checkArray(FilterLogEventsRequest request) {
        result = awsLogsClient.filterLogEvents(request);
        return !result.getEvents().isEmpty();
    }

    public void cleanUp() {
        awsLogsClient.shutdown();
    }

}
