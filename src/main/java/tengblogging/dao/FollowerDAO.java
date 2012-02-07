package tengblogging.dao;

import java.util.List;

import tengblogging.model.Follower;


/**
 * 
 * @author JunHan
 *
 */
public interface FollowerDAO {

	boolean add(Follower f);
	boolean remove(Follower f);
	
	List<Long> getAllFollower(Long userId);
	List<Long> getAllFollowing(Long userId);
}
