package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DigitalMabcDB {
	
	
	
	@RequestMapping("/digital_MABC_DB")
	public ModelAndView digitalMabcDB(ModelAndView mv) {
		
		mv.setViewName("lab/digital_MABC_DB");
		
		return mv;
		
	}
}
