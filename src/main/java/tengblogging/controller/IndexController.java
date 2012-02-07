package tengblogging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author JunHan
 *
 */
@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

//	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
//
//	private BlogService blogService;
//	
//	@Inject
//	public IndexController (BlogService blogService) {
//		this.blogService = blogService;
//	}

	@RequestMapping
	public String showIndexPage() {
//		System.out.println("get recent blogs here");
//		blogService.getRecentBlogs();
		return "index";
	}
}
