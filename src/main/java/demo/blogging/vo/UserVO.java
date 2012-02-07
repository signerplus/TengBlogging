package demo.blogging.vo;

import demo.blogging.model.User;

public class UserVO {

	private String username;
	private long id;
		
	public UserVO (User u) {
		this.username = u.getUsername();
		this.id = u.getId();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}	
}
