package demo.blogging.dao;

import java.util.List;

import demo.blogging.model.Blog;
import demo.blogging.model.User;

public interface UserDAO {

	void publishBlog (User a, Blog blog);
	
	boolean update (User u);
	
	boolean save (User u);

	User getUserByName (String username);

	User getUserById(Long id);

	List<Long> getAllUserIds();

}
