package models;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AWSLogsModel {

    private String clientRegion = System.getProperty("client.region");
    private AWSLogs awsLogsClient;
    FilterLogEventsRequest request;
    FilterLogEventsResult result;

    public void checkLambdaLog(File file) {
        awsLogsClient = AWSLogsClientBuilder.standard().withRegion(clientRegion).build();
        request = new FilterLogEventsRequest().withLogGroupName("/aws/lambda/FunctionHandler").withFilterPattern("LAMBDA " + file.getName());
        long start = System.currentTimeMillis();
        while (!checkArray(request)) {
            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;
            if(timeConsumedMillis > 30000) {
                break;
            }
        }
        result.getEvents().forEach(System.out::println);
        assertTrue(result.getEvents().get(0).getMessage().contains("LAMBDA FileName:" + file.getName()));
    }

    public boolean checkArray(FilterLogEventsRequest request) {
        result = awsLogsClient.filterLogEvents(request);
        return result.getEvents().size() >= 1;
    }
}
