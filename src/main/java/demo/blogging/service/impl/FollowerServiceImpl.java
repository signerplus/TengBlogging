package demo.blogging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.blogging.dao.FollowerDAO;
import demo.blogging.model.Follower;
import demo.blogging.service.FollowerService;

public class FollowerServiceImpl implements FollowerService {

	@Autowired
	private FollowerDAO followerDAO;
	
	@Override
	public boolean add(Follower f) {
		return followerDAO.add(f);
	}

}
