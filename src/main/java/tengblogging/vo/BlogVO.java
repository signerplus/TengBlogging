package tengblogging.vo;

import java.util.Date;

import tengblogging.model.Blog;


/**
 * 
 * @author JunHan
 *
 */
public class BlogVO {

	private String content;
	private String timeStamp;
	private String blogger;
	private Long userId;
	private Long id;

	public BlogVO(Blog blog) {
		setBlogger(blog.getUser().getUsername());
		setContent(blog.getContent());
		setId(blog.getId());
		String timeStamp = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(blog.getTimestamp().getTime()));
		setTimeStamp(timeStamp);
		setUserId(blog.getUser().getId());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getBlogger() {
		return blogger;
	}

	public void setBlogger(String blogger) {
		this.blogger = blogger;
	}	
}
