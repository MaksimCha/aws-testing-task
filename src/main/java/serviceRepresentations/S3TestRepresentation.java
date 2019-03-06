package serviceRepresentations;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class S3TestRepresentation {

    private String bucketName = System.getProperty("bucket.name");
    private String clientRegion = System.getProperty("client.region");
    private TransferManager tx;
    private AmazonS3 s3;

    public void prepareS3() {
        s3 = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
        tx = TransferManagerBuilder.standard().withS3Client(s3).build();
    }

    public void uploadFile(File file) {
        try {
            Upload myUpload = tx.upload(bucketName, file.getName(), file);
            myUpload.waitForCompletion();
            Assertions.assertTrue(myUpload.isDone());
        } catch (InterruptedException | SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(File file) {
        s3.deleteObject(bucketName, file.getName());
        try{
            s3.getObject(bucketName, file.getName());
        } catch (AmazonServiceException e){
            Assertions.assertTrue(true);
        }
    }

    public void cleanUp() {
        tx.shutdownNow();
        s3.shutdown();
    }
}
