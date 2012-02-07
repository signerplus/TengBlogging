package demo.blogging.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import demo.blogging.dao.BlogDAO;
import demo.blogging.dao.BlogTrackingDAO;
import demo.blogging.dao.FollowerDAO;
import demo.blogging.dao.UserDAO;
import demo.blogging.model.Blog;
import demo.blogging.model.User;
import demo.blogging.service.BlogService;

@Component("blogService")
public class BlogServiceImpl implements BlogService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BlogTrackingDAO blogTrackingDAO;
	
	@Autowired
	private FollowerDAO followerDAO;
	
	@Autowired
	private BlogDAO blogDAO;
	
	public BlogDAO getBlogDAO() {
		return blogDAO;
	}

	public void setBlogDAO(BlogDAO blogDAO) {
		this.blogDAO = blogDAO;
	}

	@Override
	public Blog save(String blogContent, String username) {
		User blogger = userDAO.getUserByName(username);
		
		Blog blog = blogDAO.save(blogContent, username);
		// publish to blogTracking
		List<Long> followers = followerDAO.getAllFollower(blogger.getId());
		for (Long followerId : followers) {
			blogTrackingDAO.addTracking(followerId, blogger.getId(), blog.getId());
		}
		return blog;		
	}

	@Override
	public List<Blog> getAllBlogsByUser(User u) {
		return blogDAO.getAllBlogsByUser(u);
	}

	@Override
	public Blog getBlogById(Long blogId) {
		return blogDAO.getBlogById(blogId);
	}
}
