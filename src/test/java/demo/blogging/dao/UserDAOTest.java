package demo.blogging.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.blogging.model.User;
import demo.blogging.service.PasswordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/application-context.xml" })
public class UserDAOTest {

	@Autowired
	private UserDAO userDAO;
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Test
	public void testSave() {
		User u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass");
		assertTrue(userDAO.save(u));
		
		u = new User();
		u.setUsername("test"+UUID.randomUUID());
		u.setPassword("pass1");
		assertTrue(userDAO.save(u));
	}
	
	
	@Test
	public void testGetUserByName() {
		User u1 = new User();
		String u1Name = "test"+UUID.randomUUID(); 
		u1.setUsername(u1Name);
		u1.setPassword("pass2");
		assertTrue(userDAO.save(u1));
		
		
		User u = userDAO.getUserByName(u1Name);
		assertTrue(PasswordService.getInstance().check("pass2", u.getPassword()));
	}
	
	@Test
	public void testGetAllIds() {
		List<Long> result = userDAO.getAllUserIds();
		for (Long id : result) {
			System.out.println(id);
		}
	}
}
