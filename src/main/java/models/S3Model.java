package models;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3Model {

    private String bucketName = System.getProperty("bucket.name");
    private String clientRegion = System.getProperty("client.region");
    private String ownerId = System.getProperty("owner.id");
    private TransferManager tx;
    private AmazonS3 s3Client;

    public void prepare() {
        s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        tx = TransferManagerBuilder.standard().withS3Client(s3Client).build();
    }

    public void uploadFile(File file) {
        try {
            Upload myUpload = tx.upload(bucketName, file.getName(), file);
            myUpload.waitForCompletion();
            assertTrue(myUpload.isDone());
            System.out.println("Upload done");
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
            System.out.println("Delete done");
        }
    }

    public void cleanUp(File file) {
        tx.shutdownNow();
        s3Client.shutdown();
        System.out.println("File was locally deleted: " + file.delete());
    }

    public void checkParameters() {
        assertEquals(s3Client.getBucketLocation(bucketName), clientRegion);
        assertEquals(s3Client.getRegionName(), clientRegion);
        assertEquals(s3Client.getS3AccountOwner().getId(), ownerId);
    }
}
