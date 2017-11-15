package videoupload.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import videoupload.model.User;
import videoupload.model.UserProfile;
import videoupload.model.UserVideo;
import videoupload.service.UserProfileService;
import videoupload.service.UserService;
import videoupload.service.UserVideoService;
import videoupload.util.S3UploadUtil;

@Controller
public class HomePageController {

	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserVideoService userVideoService;
	
	/*This is the landing page of the app. 
	This route redirects the user to "newuser.jsp" page where he/she can register
	*/
	@RequestMapping(value = { "/", "/newuser" }, method = RequestMethod.GET)
	public String homeLoginPage(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		return "newuser";
	}

	/*This opens "upload.jsp" page from where a user can upload videos.
	This page is only accessible by users having "ADMIN" role for their profile.
	This route gets all the videos uploaded by the user.
	*/
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		
		User currentUser = userService.findByemail(getPrincipal());
		List<UserVideo> videos = userVideoService.findAllByUserId(currentUser.getId());
		model.addAttribute("videos",videos);
		return "upload";
	}
	
	/*This is the POST API route for uploading a video.
	It returns the URL of the uploaded file to gain access to the file.
	It returns an exception page if the video was not uploaded successfully 
	and doesn't get stored in the database.
	 */	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("name") String name,
    		@RequestParam("file") MultipartFile file, ModelMap model) {

		User currentUser = userService.findByemail(getPrincipal());

		String videoUrl = null;
		
		S3UploadUtil utilObj = new S3UploadUtil();
		videoUrl = utilObj.uploadfile(file, currentUser.getId());
		
		try {
			URL urlCheck = new URL(videoUrl);
			
			UserVideo video = new UserVideo();
			video.setUrl(videoUrl);
			video.setUser(currentUser);
			userVideoService.saveVideo(video);
			return "redirect:/upload";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return videoUrl;
		}
	}

	/*If a user tries to access a page which is not allowed by his/her role, 
	he/she gets refirected to Access denied page.
	*/
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	/*This route is responsible for logging out user securely 
	by clearing the security token.
	*/
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

//	This route gets the "login.jsp" page.
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

	
	 /*This method will be called on User registration form submission.
	 It handles POST request along with validating user input.
	 */
	@RequestMapping(value = "/newuser", method = RequestMethod.POST)
	public String saveRegistration(@Valid User user,
			BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			System.out.println("There are errors");
			return "newuser";
		}
		userService.save(user);
		
		System.out.println("First Name : "+user.getFirstName());
		System.out.println("Last Name : "+user.getLastName());
		System.out.println("Password : "+user.getPassword());
		System.out.println("Email : "+user.getEmail());
		System.out.println("Checking UserProfiles....");
		if(user.getUserProfiles()!=null){
			for(UserProfile profile : user.getUserProfiles()){
				System.out.println("Profile : "+ profile.getType());
			}
		}
		
		model.addAttribute("success", "User " + user.getFirstName() + " has been registered successfully");
		return "registrationsuccess";
	}

	/*This is an internal method used to get User details 
	of the logged in user from the Security context
	*/
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

}