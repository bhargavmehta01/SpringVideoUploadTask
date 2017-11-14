package videoupload.model;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

public class FileUpload {

	private static final String[] ALLOWED_FILE_TYPES = {"image/jpeg", "image/jpg", "image/gif"};
    private static final Long MAX_FILE_SIZE = 10485760L; //1MB
    private static final String UPLOAD_FILE_PATH = "src/main/resources/images/";
    
    public String process(MultipartFile file) {
    	
        if (!file.isEmpty()) {
            String contentType = file.getContentType().toString().toLowerCase();
            if (isValidContentType(contentType)) {
                if (belowMaxFileSize(file.getSize())) {
                    String newFile = UPLOAD_FILE_PATH + file.getOriginalFilename();
                    try {
                        file.transferTo(new File(newFile));
                        return "You have successfully uploaded " + file.getOriginalFilename() + "!";
                    } catch (IllegalStateException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    } catch (IOException e) {
                        return "There was an error uploading " + file.getOriginalFilename() + " => " + e.getMessage();
                    }
                } else {
                    return "Error. " + file.getOriginalFilename() + " file size (" + file.getSize() + ") exceeds " + MAX_FILE_SIZE + " limit.";
                }
            } else {
                return "Error. " + contentType + " is not a valid content type.";
            }
        } else {
            return "Error. No file choosen.";
        }
    }
    
    private Boolean isValidContentType(String contentType) {
        if (!Arrays.asList(ALLOWED_FILE_TYPES).contains(contentType)) {
            return false;
        }
        
        return true;
    }
    
    private Boolean belowMaxFileSize(Long fileSize) {
        if (fileSize > MAX_FILE_SIZE) {
            return false;
        }
        
        return true;
    }
}
