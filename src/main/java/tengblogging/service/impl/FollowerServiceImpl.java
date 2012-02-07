package tengblogging.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import tengblogging.dao.FollowerDAO;
import tengblogging.model.Follower;
import tengblogging.service.FollowerService;


/**
 * 
 * @author JunHan
 *
 */
public class FollowerServiceImpl implements FollowerService {

	@Autowired
	private FollowerDAO followerDAO;
	
	@Override
	public boolean add(Follower f) {
		return followerDAO.add(f);
	}

}
