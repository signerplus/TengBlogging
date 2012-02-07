package demo.blogging.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.blogging.dao.UserDAO;
import demo.blogging.model.Blog;
import demo.blogging.model.User;
import demo.blogging.service.PasswordService;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

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
	public boolean update (User u) {
		String plainPassword = u.getPassword();
		String encrypted = PasswordService.getInstance().encode(plainPassword);
		u.setPassword(encrypted);
		System.out.println("user updated");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(u);
		session.getTransaction().commit();
		session.close();
		return true;
	}
	
	@Transactional
	@Override
	public boolean save(User u) {
		String plainPassword = u.getPassword();
		String encrypted = PasswordService.getInstance().encode(plainPassword);
		u.setPassword(encrypted);
		System.out.println("user saved");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(u);
		session.getTransaction().commit();
		session.close();
		return true;
	}

	@Transactional
	@Override
	public User getUserByName(String username) {
		System.out.println("user saved");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Object userObj = session.createQuery("from User where username = :name").setString("name", username).uniqueResult();
		User u = (User)userObj;
		
		session.getTransaction().commit();
		session.close();
		return u;
	}
	
	@Transactional
	@Override
	public void publishBlog(User a, Blog blog) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		a.getBlogs().add(blog);
		session.update(a);
		
		session.getTransaction().commit();
		session.close();
	}

//	@Transactional
//	@Override
//	public Set<User> listAllUsersNotFollowedYet(String username) {
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//
//		User u = (User)session.createQuery("from User where username = :name").setString("name", username).uniqueResult();
//		Set<User> currentFollowing = u.getFollowings();
//		
//		List<User> allUsersExceptMe = session.createQuery("from User where username != :name").setString("name", username).list();
//		allUsersExceptMe.removeAll(currentFollowing);
//		
//		Set<User> result = new HashSet<User>();
//		for (User user : allUsersExceptMe) {
//			result.add(user);
//		}
//		
//		session.getTransaction().commit();
//		session.close();
//
//		return result;
//	}
	
	@Transactional
	@Override
	public User getUserById(Long id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		User result = (User)session.get(User.class, id);
		
		session.getTransaction().commit();
		session.close();

		return result;
	}

	@Transactional
	@Override
	public List<Long> getAllUserIds() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		List<Long> result = session.createQuery("select user.id from User as user").list();
		
		session.getTransaction().commit();
		session.close();
		return result;
	}
	
//	@Transactional
//	@Override
//	public void addBlogTracking(User a, User b) {
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		
//		session.update(a);
//		List<Blog> result = session.createQuery("from Blog where USER_ID = :id").setLong("id", b.getId()).list();
//		a.getBlogTracking().addAll(result);
//		session.update(a);
//		
//		session.getTransaction().commit();
//		session.close();
//	}

//	@Transactional
//	@Override
//	public List<Blog> getBlogTracking(String name) {
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		
//		User u = getUserByName(name);
//		
//		List<Blog> result = session.createQuery("from Blog where USER_ID = :id").setLong("id", b.getId()).list();
//		a.getBlogTracking().addAll(result);
//		session.update(a);
//		
//		session.getTransaction().commit();
//		session.close();
//		return null;
//	}
}
