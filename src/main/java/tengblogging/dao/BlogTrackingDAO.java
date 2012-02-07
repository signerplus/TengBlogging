package tengblogging.dao;

import java.util.List;

import tengblogging.model.BlogTracking;


/**
 * 
 * @author JunHan
 *
 */
public interface BlogTrackingDAO {

	boolean addTracking(Long followerId, Long bloggerId, Long blogId);
	
	boolean removeTracking(Long followerId, Long bloggerId);

	List<BlogTracking> getTrackingByUser(Long followerId);	
}
