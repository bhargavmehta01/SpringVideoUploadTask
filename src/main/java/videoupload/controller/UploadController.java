package videoupload.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.auth.profile.ProfileCredentialsProvider;
//import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import videoupload.util.S3UploadUtil;


@Controller
public class UploadController {

	
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

//				logger.info("Server File Location="
//						+ serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name
					+ " because the file was empty.";
		}
	}
	
	/*private static final long serialVersionUID = 1L;

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	FileItemFactory factory = new DiskFileItemFactory();
	ServletFileUpload upload = new ServletFileUpload(factory);
	Iterator<FileItem> iterator = null;
	String documentType = null;
	InputStream fileIS=null;
	String filename=null;
	try {
	iterator = upload.parseRequest(request).iterator();
	} catch (FileUploadException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}

	while (iterator.hasNext()) {
	FileItem item = iterator.next();
	if (!item.isFormField()) {
	documentType=item.getContentType();
	fileIS=item.getInputStream();
	filename=item.getName();
	}
	}

	Properties prop = new Properties();

	InputStream propstream = new FileInputStream(getServletContext().getRealPath("src/main/resources/s3.properties"));
	prop.load(propstream);
	AWSCredentials credentials = new BasicAWSCredentials(
	prop.getProperty("AWSAccessKeyId"),
	prop.getProperty("AWSSecretKey"));
	String bucketName=prop.getProperty("bucketName");

	S3UploadUtil s3client = new S3UploadUtil();
	ObjectMetadata metadata=new ObjectMetadata();
	metadata.setContentLength(Long.valueOf(fileIS.available()));
	s3client.uploadfile(credentials, bucketName, filename, fileIS, metadata);
	response.sendRedirect("output.jsp");
	}*/
}
