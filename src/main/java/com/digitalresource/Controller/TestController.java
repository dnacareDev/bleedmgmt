package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController
{
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView mv)
	{
		mv.setViewName("login/login");
		
		return mv;
	}
	@RequestMapping("/home")
	public ModelAndView gethome(ModelAndView mv)
	{
		mv.setViewName("home/resource_home");
		
		return mv;
	}
	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv) {
		mv.setViewName("genome/breed");
		
		return mv;
	}
	@RequestMapping("/basic")
	public ModelAndView basic(ModelAndView mv) {
		mv.setViewName("genome/basic");
		
		return mv;
	}
	@RequestMapping("/layout1")
	public ModelAndView layout1(ModelAndView mv) {
		mv.setViewName("layout/layout1");
		
		return mv;
	}
	@RequestMapping("/layout2")
	public ModelAndView layout2(ModelAndView mv) {
		mv.setViewName("layout/layout2");
		
		return mv;
	}
	@RequestMapping("/income")
	public ModelAndView income(ModelAndView mv) {
		mv.setViewName("genome/income");
		
		return mv;
	}
	@RequestMapping("/sample")
	public ModelAndView sample(ModelAndView mv) {
		mv.setViewName("genome/sample");
		
		return mv;
	}
}