package videoupload.dao;

import videoupload.model.User;

public interface UserDao {

	void save(User user);
	
	User findById(int id);
	
	User findByemail(String email);
	
}

