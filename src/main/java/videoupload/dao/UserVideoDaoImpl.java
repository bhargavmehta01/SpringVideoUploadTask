package videoupload.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import videoupload.model.UserVideo;

@Repository("userVideoDao")
public class UserVideoDaoImpl extends AbstractDao<Integer, UserVideo> implements UserVideoDao{

	@SuppressWarnings("unchecked")
    public List<UserVideo> findAll() {
        Criteria crit = createEntityCriteria();
        return (List<UserVideo>)crit.list();
    }
 
    public void save(UserVideo video) {
        persist(video);
    }
 
     
    public UserVideo findById(int id) {
        return getByKey(id);
    }
 
    @SuppressWarnings("unchecked")
    public List<UserVideo> findAllByUserId(int userId){
        Criteria crit = createEntityCriteria();
        crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria userCriteria = crit.createCriteria("user");
        userCriteria.add(Restrictions.eq("id", userId));
        return (List<UserVideo>)crit.list();
    }
 
     
    public void deleteById(int id) {
    	UserVideo video =  getByKey(id);
        delete(video);
    }
    
}
