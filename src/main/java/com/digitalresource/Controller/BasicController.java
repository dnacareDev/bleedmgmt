package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BasicController {
	@RequestMapping("/basic")
	public ModelAndView basic(ModelAndView mv) {
		mv.setViewName("genome/basic");
		
		return mv;
	}
}
