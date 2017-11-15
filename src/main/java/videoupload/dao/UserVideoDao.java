package videoupload.dao;

import java.util.List;

import videoupload.model.*;

public interface UserVideoDao {

	List<UserVideo> findAll();
    
	UserVideo findById(int id);
     
    void save(UserVideo video);
     
    List<UserVideo> findAllByUserId(int userId);
     
    void deleteById(int id);
}
