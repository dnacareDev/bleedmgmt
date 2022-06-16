package com.digitalresource.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.LogList;
import com.digitalresource.Service.LogService;

@Controller
public class LogController {

	@Autowired
	private LogService logService;
	
	@RequestMapping("/log")
	public ModelAndView getLog(ModelAndView mv) {

		mv.setViewName("resource/log");

	    return mv;
	}
	
	@ResponseBody
	@RequestMapping("/searchLog")
	public Map<String, Object> searchResource() {
		Map<String, Object> result = new HashMap<>();
		System.out.println("aaa");
		List<LogList> logList = logService.searchLog();
		result.put("logList", logList);
		
		return result;
	}
	
}
