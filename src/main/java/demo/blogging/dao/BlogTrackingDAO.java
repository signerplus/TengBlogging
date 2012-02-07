package demo.blogging.dao;

import java.util.List;

import demo.blogging.model.BlogTracking;

public interface BlogTrackingDAO {

	boolean addTracking(Long followerId, Long bloggerId, Long blogId);
	
	boolean removeTracking(Long followerId, Long bloggerId);

	List<BlogTracking> getTrackingByUser(Long followerId);	
}
