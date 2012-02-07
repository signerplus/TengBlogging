package demo.blogging.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.blogging.model.Follower;
import demo.blogging.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/application-context.xml" })
public class FollowerDAOTest {

	@Autowired
	private FollowerDAO followerDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Test
	public void testGetAllFollower() {
		User u0 = new User();
		u0.setUsername("test"+UUID.randomUUID());
		u0.setPassword("pass");
		assertTrue(userDAO.save(u0));
		
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass3");
		userDAO.save(u);
		
		User u2 = new User();
		u2.setUsername("test"+UUID.randomUUID());
		u2.setPassword("pass3");
		userDAO.save(u2);
		
		Follower f = new Follower();
		f.setUser(u0.getId());
		f.setFollower(u.getId());
		followerDAO.add(f);
		
		f = new Follower();
		f.setUser(u0.getId());
		f.setFollower(u2.getId());
		followerDAO.add(f);
		
		f = new Follower();
		f.setUser(u.getId());
		f.setFollower(u0.getId());
		followerDAO.add(f);	
		
		
		List<Long> result = followerDAO.getAllFollower(u0.getId());
		Assert.assertEquals(2, result.size());
	}
}
