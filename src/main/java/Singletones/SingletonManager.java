package Singletones;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class SingletonManager {

    private static AWSLogs awsLogs;
    private static AmazonDynamoDB dynamoDBClient;
    private static AWSLambda lambdaClient;
    private static AmazonS3 s3Client;

    public static synchronized AWSLogs getAwsLogs() {
        if (awsLogs == null) {
            awsLogs = AWSLogsClientBuilder
                    .standard()
                    .withRegion(System.getProperty("client.region"))
                    .build();
        }
        return awsLogs;
    }

    public static synchronized AmazonDynamoDB getDynamoDBClient() {
        if (dynamoDBClient == null) {
            dynamoDBClient = AmazonDynamoDBClientBuilder
                    .standard()
                    .withRegion(System.getProperty("client.region"))
                    .build();
        }
        return dynamoDBClient;
    }

    public static synchronized AWSLambda getLambdaClient() {
        if (lambdaClient == null) {
            lambdaClient = AWSLambdaClientBuilder
                    .standard()
                    .withRegion(System.getProperty("client.region"))
                    .build();
        }
        return lambdaClient;
    }

    public static synchronized AmazonS3 getS3Client() {
        if (s3Client == null) {
            s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(System.getProperty("client.region"))
                    .build();
        }
        return s3Client;
    }

    public static void tearDown() {
        if(awsLogs != null) {
            awsLogs.shutdown();
            awsLogs = null;
        }
        if(dynamoDBClient != null) {
            dynamoDBClient.shutdown();
            dynamoDBClient = null;
        }
        if(lambdaClient != null) {
            lambdaClient.shutdown();
            lambdaClient = null;
        }
        if(s3Client != null) {
            s3Client.shutdown();
            s3Client = null;
        }
    }
}
