package tengblogging.dao;

import java.util.List;

import tengblogging.model.Blog;
import tengblogging.model.User;


/**
 * 
 * @author JunHan
 *
 */
public interface UserDAO {

	void publishBlog (User a, Blog blog);
	
	boolean update (User u);
	
	boolean save (User u);

	User getUserByName (String username);

	User getUserById(Long id);

	List<Long> getAllUserIds();

}
