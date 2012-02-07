package demo.blogging.dao;

import java.util.List;

import demo.blogging.model.Follower;

public interface FollowerDAO {

	boolean add(Follower f);
	boolean remove(Follower f);
	
	List<Long> getAllFollower(Long userId);
	List<Long> getAllFollowing(Long userId);
}
