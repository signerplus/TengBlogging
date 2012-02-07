package demo.blogging.dao;

import java.util.List;

import demo.blogging.model.Blog;
import demo.blogging.model.User;

public interface BlogDAO {
	
	Blog save(String blogContent, String username);

	List<Blog> getAllBlogsByUser(User u);

	Blog getBlogById(Long blogId);

}
