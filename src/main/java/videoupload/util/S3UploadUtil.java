package videoupload.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3UploadUtil {

	private static final String AWS_AccessKey_Id = "";
	private static final String AWS_SecretKey = "";
	private static final String BucketName = "";

	/*This is the utility method enabling AWS S3 file uploads.
	It takes in file and the User Id as parameters to store 
	it to the respective user's folder on AWS S3.
	*/
	public String uploadfile(MultipartFile file, int userId)
	{
		AWSCredentials credentials = new BasicAWSCredentials(AWS_AccessKey_Id, AWS_SecretKey);

		@SuppressWarnings("deprecation")
		AmazonS3 s3Client = new AmazonS3Client(credentials);
		try {
			InputStream is = file.getInputStream();

			ObjectMetadata metadata=new ObjectMetadata();
			metadata.setContentLength(Long.valueOf(is.available()));

			String fileLocation = BucketName+"/videos/"+userId;

			PutObjectRequest putobj = new PutObjectRequest(fileLocation, file.getOriginalFilename(), is, metadata);
			putobj.setCannedAcl(CannedAccessControlList.AuthenticatedRead);
			s3Client.putObject(putobj);

			Date expiration = new Date();
			long mseconds = expiration.getTime();
			mseconds += 2000 * 60 * 60;
			expiration.setTime(mseconds);

			GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(fileLocation, file.getOriginalFilename());
			urlRequest.setExpiration(expiration);
			URL url = s3Client.generatePresignedUrl(urlRequest);

			return url.toString();
		}
		catch (AmazonServiceException ase) {
			return "Caught an AmazonServiceException, which " +
					"means your request made it " +
					"to Amazon S3, but was rejected with an error response" +
					" for some reason.";
		} 
		catch (AmazonClientException ace) {
			return "Error Message: " + ace.getMessage();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			return e.toString();
		}
	}

}
