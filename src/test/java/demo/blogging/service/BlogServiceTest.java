package demo.blogging.service;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.blogging.model.Blog;
import demo.blogging.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/application-context.xml" })

public class BlogServiceTest {

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
	public void testSave() {
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass");
		userService.save(u);
		
		String blogContent = "some random content";

		Assert.assertNotNull(blogService.save(blogContent, u.getUsername()));
		
		
		String blogContent2 = "some random content 2";
		Assert.assertNotNull(blogService.save(blogContent2, u.getUsername()));
	}

	@Test
	public void testGetAllBlogs() {
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass");
		userService.save(u);
		
		String blogContent = "some random content";

		Assert.assertNotNull(blogService.save(blogContent, u.getUsername()));
		
		String blogContent2 = "some random content 2";
		Assert.assertNotNull(blogService.save(blogContent2, u.getUsername()));
		
		List<Blog> result = blogService.getAllBlogsByUser(u);
		Assert.assertEquals(2, result.size());
		for (Blog blog : result) {
			System.out.println(blog);
		}
	}
}
