package tengblogging.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tengblogging.dao.BlogDAO;
import tengblogging.model.Blog;
import tengblogging.model.User;


/**
 * 
 * @author JunHan
 *
 */
@Repository("blogDAO")
@Transactional
public class BlogDAOImpl implements BlogDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public Blog save(String blogContent, String username) {
		System.out.println("blog saved");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Object userObj = session.createQuery("from User where username = :name").setString("name", username).uniqueResult();
		User u = (User)userObj;
		
		Blog b = new Blog();
		b.setContent(blogContent);
		b.setUser(u);
		b.setTimestamp(new Timestamp(System.currentTimeMillis()));
		
		session.save(b);
		
		session.getTransaction().commit();
		session.close();
		return b;
	}

	@Transactional
	@Override
	public List<Blog> getAllBlogsByUser(User u) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		List result = session.createQuery("from Blog where USER_ID = :id").setLong("id", u.getId()).list();
		
		session.getTransaction().commit();
		session.close();

		return result;
	}

	@Transactional
	@Override
	public Blog getBlogById(Long blogId) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Blog result = (Blog)session.get(Blog.class, blogId);
		
		session.getTransaction().commit();
		session.close();

		return result;
	}
}
