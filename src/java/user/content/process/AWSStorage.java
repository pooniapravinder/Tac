package user.content.process;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class AWSStorage {

    public String SaveFileToAWS(MultipartFile multipartFile, String filename, String bucketName, String contentType) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(AWSConstants.ACCESS_KEY_ID, AWSConstants.ACCESS_SEC_KEY);
        AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();
        String result = "Upload unsuccessful because ";
        try {
            try (S3Object s3Object = new S3Object()) {
                ObjectMetadata omd = new ObjectMetadata();
                omd.setContentLength(multipartFile.getSize());
                omd.setContentType(contentType);
                omd.setContentDisposition("inline; filename=" + filename);
                ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());
                s3Object.setObjectContent(bis);
                s3.putObject(new PutObjectRequest(bucketName, filename, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
            }
            result = "Uploaded Successfully.";
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was " + "rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            result = result + ase.getMessage();
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered an internal error while " + "trying to communicate with S3, such as not being able to access the network.");
            result = result + ace.getMessage();
        } catch (IOException e) {
            result = result + e.getMessage();
        }
        return result;
    }

}
