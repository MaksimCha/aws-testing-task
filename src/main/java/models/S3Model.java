package models;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

import static Singletones.SingletonManager.getS3Client;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3Model {

    private TransferManager tx;
    private static final Logger log = LogManager.getLogger(S3Model.class);

    public void uploadFile(File file) {
        try {
            tx = TransferManagerBuilder.standard().withS3Client(getS3Client()).build();
            Upload myUpload = tx.upload(System.getProperty("bucket.name"), file.getName(), file);
            myUpload.waitForCompletion();
            checkUpload(myUpload);
            log.info("Upload done");
        } catch (InterruptedException | SdkClientException e) {
            e.printStackTrace();
        }
    }

    private void checkUpload(Upload upload){
        assertTrue(upload.isDone(), "Upload was done: ");
    }

    public void deleteFile(File file) {
        getS3Client().deleteObject(System.getProperty("bucket.name"), file.getName());
        checkDelete(file);
    }

    private void checkDelete(File file) {
        try {
            getS3Client().getObject(System.getProperty("bucket.name"), file.getName());
            log.info("Delete is undone");
        } catch (AmazonServiceException e) {
            assertTrue(true);
            log.info("Delete done");
        }
    }

    public void deleteLocalFile(File file) {
        log.info("File was locally deleted: " + file.delete());
    }

    public void checkParameters() {
        assertEquals(getS3Client().getBucketLocation(System.getProperty("bucket.name")), System.getProperty("client.region"), "Bucket region is correct: ");
        assertEquals(getS3Client().getRegionName(), System.getProperty("client.region"), "S3 region is correct: ");
        assertEquals(getS3Client().getS3AccountOwner().getId(), System.getProperty("owner.id"), "S3 owner is correct: ");
        log.info("Check S3 client parameters done");
    }

    public void tearDown() {
        tx.shutdownNow();
    }
}
