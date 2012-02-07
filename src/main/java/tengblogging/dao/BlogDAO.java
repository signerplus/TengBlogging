package tengblogging.dao;

import java.util.List;

import tengblogging.model.Blog;
import tengblogging.model.User;


/**
 * 
 * @author JunHan
 *
 */
public interface BlogDAO {
	
	Blog save(String blogContent, String username);

	List<Blog> getAllBlogsByUser(User u);

	Blog getBlogById(Long blogId);

}
