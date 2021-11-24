package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv)
	{
		mv.setViewName("login/login");
		
		return mv;
	}
	
	@RequestMapping("/")
	public ModelAndView index(ModelAndView mv) {
		mv.setViewName("login/login");
		return mv;
	}
}
