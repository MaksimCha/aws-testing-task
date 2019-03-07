package models;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;

import java.io.File;

public class AWSLogsModel {

    private String clientRegion = System.getProperty("client.region");
    private AWSLogs awsLogsClient;

    public void checkLambdaLog(File file) {
        awsLogsClient = AWSLogsClientBuilder.standard().withRegion(clientRegion).build();

        FilterLogEventsRequest request = new FilterLogEventsRequest().withInterleaved(true)
                .withLogGroupName("/aws/lambda/FunctionHandler").withFilterPattern("LAMBDA FileName");
        FilterLogEventsResult result = awsLogsClient.filterLogEvents(request);
        result.getEvents().forEach(System.out::println);
        System.out.println(result.getEvents().get(result.getEvents().size() - 1).getMessage());
        //assertTrue(result.getEvents().get(result.getEvents().size()-1).getMessage().contains("LAMBDA FileName:" + file.getName()));
    }
}
