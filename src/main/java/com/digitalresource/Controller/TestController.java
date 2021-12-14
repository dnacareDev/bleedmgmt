package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController
{
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

	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mv){
		mv.setViewName("lab/data_manage");
		return mv;
	}
}