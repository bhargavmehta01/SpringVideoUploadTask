package videoupload.service;

import videoupload.model.User;

public interface UserService {

	void save(User user);
	
	User findById(int id);
	
	User findByemail(String email);
	
}