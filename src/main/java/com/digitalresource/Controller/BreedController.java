package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BreedController {
	
	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv) {
		mv.setViewName("genome/breed");
		
		return mv;
	}
}
