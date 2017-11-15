package videoupload.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import videoupload.dao.UserVideoDao;
import videoupload.model.UserVideo;

@Service("userVideoService")
@Transactional
public class UserVideoServiceImpl implements UserVideoService {

	@Autowired
    UserVideoDao dao;
 
    public UserVideo findById(int id) {
        return dao.findById(id);
    }
 
    public List<UserVideo> findAll() {
        return dao.findAll();
    }
 
    public List<UserVideo> findAllByUserId(int userId) {
        return dao.findAllByUserId(userId);
    }
     
    public void saveVideo(UserVideo video){
        dao.save(video);
    }
 
    public void deleteById(int id){
        dao.deleteById(id);
    }

}
