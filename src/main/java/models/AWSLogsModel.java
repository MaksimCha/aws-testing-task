package models;

import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;
import enums.LogEvents;

public class AWSLogsModel {

    private String clientRegion = System.getProperty("client.region");
    private AWSLogs awsLogsClient;

    public void checkLambdaLog(LogEvents event) {
        awsLogsClient = AWSLogsClientBuilder.standard().withRegion(clientRegion).build();
        FilterLogEventsRequest request = new FilterLogEventsRequest()
                .withLogGroupName("/aws/lambda/FunctionHandler").withLogStreamNamePrefix("2019/03/07/[$LATEST]");
        FilterLogEventsResult result = awsLogsClient.filterLogEvents(request);
        result.getEvents().forEach(System.out::println);
    }
}
