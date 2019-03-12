package models;

import com.amazonaws.services.logs.model.FilterLogEventsRequest;
import com.amazonaws.services.logs.model.FilterLogEventsResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.core.ConditionTimeoutException;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static Singletones.SingletonManager.getAwsLogs;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AWSLogsModel {

    private FilterLogEventsResult result;
    private static final Logger log = LogManager.getLogger(AWSLogsModel.class);


    public void sendRequestLog(File file) {
        FilterLogEventsRequest request = new FilterLogEventsRequest()
                .withLogGroupName("/aws/lambda/FunctionHandler")
                .withFilterPattern("LAMBDA " + file.getName()).withStartTime(System.currentTimeMillis() - 1000);
        try {
            await()
                    .atMost(30, TimeUnit.SECONDS)
                    .pollInterval(1, TimeUnit.SECONDS)
                    .until(() -> checkArray(request));
            checkLog(file);
            log.info("Log message is correct");
        } catch (ConditionTimeoutException e) {
            log.catching(e);
            log.error("Needed log message does not exist");
        }
    }

    private void checkLog(File file) {
        assertTrue(result.getEvents().get(0).getMessage().contains("LAMBDA FileName:" + file.getName()),"Log message was find out: ");
    }

    private boolean checkArray(FilterLogEventsRequest request) {
        result = getAwsLogs().filterLogEvents(request);
        return !result.getEvents().isEmpty();
    }
}
