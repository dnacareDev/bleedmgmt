package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LabController
{
	@RequestMapping("matrix")
	public ModelAndView Matrix(ModelAndView mv)
	{
		mv.setViewName("lab/matrix");
		
		return mv;
	}
}