package models;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3Model {

    private String bucketName = System.getProperty("bucket.name");
    private String clientRegion = System.getProperty("client.region");
    private String ownerId = System.getProperty("owner.id");
    private TransferManager tx;
    private AmazonS3 s3Client;
    private static final Logger log = LogManager.getLogger(S3Model.class);

    public void prepare() {
        s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        tx = TransferManagerBuilder.standard().withS3Client(s3Client).build();
    }

    public void uploadFile(File file) {
        try {
            Upload myUpload = tx.upload(bucketName, file.getName(), file);
            myUpload.waitForCompletion();
            assertTrue(myUpload.isDone());
            log.info("Upload done");
        } catch (InterruptedException | SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(File file) {
        s3Client.deleteObject(bucketName, file.getName());
        try {
            s3Client.getObject(bucketName, file.getName());
        } catch (AmazonServiceException e) {
            assertTrue(true);
            log.info("Delete done");
        }
    }

    public void cleanUp(File file) {
        tx.shutdownNow();
        s3Client.shutdown();
        log.info("File was locally deleted: " + file.delete());
    }

    public void checkParameters() {
        assertEquals(s3Client.getBucketLocation(System.getProperty("bucket.name")), System.getProperty("client.region"));
        assertEquals(s3Client.getRegionName(), System.getProperty("client.region"));
        assertEquals(s3Client.getS3AccountOwner().getId(), System.getProperty("owner.id"));
        log.info("Check S3 client parameters done");
    }
}
