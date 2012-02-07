package demo.blogging.service;

import java.util.List;

import demo.blogging.model.Blog;
import demo.blogging.model.User;

public interface BlogService {
	
	Blog save(String blogContent, String username);

	List<Blog> getAllBlogsByUser(User u);

	Blog getBlogById(Long blogId);
	
}
