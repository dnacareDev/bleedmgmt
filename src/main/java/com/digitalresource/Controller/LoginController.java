package com.digitalresource.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.User;
import com.digitalresource.Service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	
	@ResponseBody
	@RequestMapping("updatePassword")
	public int UpdatePassword(Authentication auth, @RequestParam("user_password") String user_password)
	{
		String password = passwordEncoder.encode(user_password);
		
		User user = (User)auth.getPrincipal();
		user.setUser_password(password);
		
		int result = service.UpdatePassword(user);
		
		return result;
	}
}
