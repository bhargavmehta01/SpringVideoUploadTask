package videoupload.service;

import java.util.List;

import videoupload.model.UserVideo;

public interface UserVideoService {

	UserVideo findById(int id);
	 
    List<UserVideo> findAll();
     
    List<UserVideo> findAllByUserId(int id);
     
    void saveVideo(UserVideo video);
     
    void deleteById(int id);
}
