package videoupload.controller;


import org.springframework.stereotype.Controller;

@Controller
public class UploadController {

	
//	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
//	public @ResponseBody
//	String uploadFileHandler(@RequestParam("name") String name,
//			@RequestParam("file") MultipartFile file) {
//
//		if (!file.isEmpty()) {
//			try {
//				byte[] bytes = file.getBytes();
//
//				// Creating the directory to store file
//				String rootPath = System.getProperty("catalina.home");
//				File dir = new File(rootPath + File.separator + "tmpFiles");
//				if (!dir.exists())
//					dir.mkdirs();
//
//				// Create the file on server
//				File serverFile = new File(dir.getAbsolutePath()
//						+ File.separator + name);
//				BufferedOutputStream stream = new BufferedOutputStream(
//						new FileOutputStream(serverFile));
//				stream.write(bytes);
//				stream.close();
//
////				logger.info("Server File Location="
////						+ serverFile.getAbsolutePath());
//
//				return "You successfully uploaded file=" + name;
//			} catch (Exception e) {
//				return "You failed to upload " + name + " => " + e.getMessage();
//			}
//		} else {
//			return "You failed to upload " + name
//					+ " because the file was empty.";
//		}
//	}
	
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
