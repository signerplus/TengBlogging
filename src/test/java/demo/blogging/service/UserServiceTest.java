package demo.blogging.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.blogging.model.Blog;
import demo.blogging.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/application-context.xml" })
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private BlogService blogService;
	
	public void setBlogService(BlogService blogService) {
		this.blogService = blogService;
	}

	@Test
	public void testAddAndFollower() {
		User u1 = new User();
		u1.setUsername("test"+UUID.randomUUID());
		u1.setPassword("pass2");
		userService.save(u1);
		
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass3");
		userService.save(u);
		
		User u2 = new User();
		u2.setUsername("test"+UUID.randomUUID());
		u2.setPassword("pass3");
		userService.save(u2);
		
		userService.followUser(u1, u2);
		userService.followUser(u1, u);
	}

	@Test
	public void testPublish() {
		
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass2");
		userService.save(u);
		
		String blogContent = "some random content";
		Blog uBlog = blogService.save(blogContent, u.getUsername());
		userService.publishBlog(u, uBlog);
	}
	
	@Test
	public void testAddFollowerAndPublish() {
		User u1 = new User();
		u1.setUsername("test"+UUID.randomUUID());
		u1.setPassword("pass2");
		userService.save(u1);
		System.out.println("u1 id = " + u1.getId());
		
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass3");
		userService.save(u);
		
		// u1 follows u, so every time u publish something, notify u1
		userService.followUser(u1, u);
		
		String blogContent = "some random content";
		Blog uBlog = blogService.save(blogContent, u.getUsername());
		userService.publishBlog(u, uBlog);

		String blogContent1 = "u publish some random content 1";
		Blog uBlog1 = blogService.save(blogContent1, u.getUsername());
		System.out.println("id1 = " + uBlog1.getId());
		
		userService.publishBlog(u, uBlog1);
	}

	
	@Test
	public void testlistAllUsersNotFollowedYet() {
		User u1 = new User();
		String u1Name = "test"+UUID.randomUUID(); 
		u1.setUsername(u1Name);
		u1.setPassword("pass2");
		Assert.assertTrue(userService.save(u1));
		System.out.println("u1 id = " + u1.getId());
		
		User u2 = new User();
		u2.setUsername("test"+UUID.randomUUID());
		u2.setPassword("pass3");
		userService.save(u2);
		System.out.println("u2 id = " + u2.getId());
		
		User u3 = new User();
		u3.setUsername("test"+UUID.randomUUID());
		u3.setPassword("pass3");
		userService.save(u3);
		System.out.println("u3 id = " + u3.getId());
		
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass3");
		userService.save(u);
		
		u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass3");
		userService.save(u);
		
		userService.followUser(u2, u1);
		userService.followUser(u3, u1);
		
		Set<User> notFollowedYet = userService.listAllUsersNotFollowedYet(u1Name);
		for (User user : notFollowedYet) {
			System.out.println(user.getId());
		}
	}
	
	@Test
	public void testAddTrackingBlog() {
		User u1 = new User();
		String u1Name = "test"+UUID.randomUUID(); 
		u1.setUsername(u1Name);
		u1.setPassword("pass2");
		Assert.assertTrue(userService.save(u1));
		System.out.println("u1 id = " + u1.getId());
		
		User u2 = new User();
		u2.setUsername("test"+UUID.randomUUID());
		u2.setPassword("pass3");
		userService.save(u2);
		
		// u2 publish blogs
		String blogContent = "some random content";

		Assert.assertNotNull(blogService.save(blogContent, u2.getUsername()));
		
		
		String blogContent2 = "some random content 2";
		Assert.assertNotNull(blogService.save(blogContent2, u2.getUsername()));
				
		System.out.println("u2 id = " + u2.getId());				
		
		// u1 follows u2
		userService.followUser(u1, u2);
		
		
		// u1 should have a list of blogs published by u2
		List<Blog> tracking = userService.getBlogTracking(u1Name);
		System.out.println(tracking.size());
		
		// u2 continue to publish blog
		String blogContent3 = "some random content 3";
		Assert.assertNotNull(blogService.save(blogContent3, u2.getUsername()));
		
		// should be old size +1
		tracking = userService.getBlogTracking(u1Name);
		System.out.println(tracking.size());
	}
}
