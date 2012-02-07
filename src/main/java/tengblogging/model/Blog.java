package tengblogging.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author JunHan
 *
 */
@Entity
@Table(name = "BLOGS")
public class Blog {

	@Id
	@Column(name = "BLOG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private java.sql.Timestamp timestamp;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.sql.Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.sql.Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {		
		this.user = user;
		user.getBlogs().add(this);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", timestamp=" + timestamp + ", user=" + user
				+ ", content=" + content + "]";
	}
	
}
