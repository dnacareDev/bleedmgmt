package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DigitalAnalysisController {
	
	
	@RequestMapping("/digital_analysis")
	public ModelAndView digital_analysis(ModelAndView mv) {
		
		mv.setViewName("lab/digital_analysis");
		
		return mv;
		
	}
}
