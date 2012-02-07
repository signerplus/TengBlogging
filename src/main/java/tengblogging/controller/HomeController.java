package tengblogging.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author JunHan
 *
 */
@Controller
@RequestMapping("home")
public class HomeController {

	@RequestMapping
	public String showHomePage() {
		return "home";
	}
}
