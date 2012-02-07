package demo.blogging.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String username;
	private String password;
	
	@OneToMany
	@JoinTable(name = "User_blogs", 
		joinColumns = { @JoinColumn(table = "USERS", name = "Users_ID", referencedColumnName = "USER_ID") }, 
		inverseJoinColumns = { @JoinColumn(table = "BLOGS", name = "User_BLOG_ID", referencedColumnName = "BLOG_ID") }
	)
	private List<Blog> blogs = new ArrayList<Blog>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}
}
