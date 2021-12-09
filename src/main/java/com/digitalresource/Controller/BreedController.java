package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BreedController {

	@Autowired
	private CropService cropService;
	
	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv) {
		List<Crop> cropList = cropService.selectCropList();

		mv.addObject("cropList", cropList);

		mv.setViewName("genome/breed");
		
		return mv;
	}

	@ResponseBody
	@RequestMapping("searchBreed")
	public Map<String, Object> SearchBreed(Authentication auth, @RequestParam(value = "breed_name", required = false) String breed_name, @RequestParam(value = "page_num", required = false) int page_num, @RequestParam(value = "limit", required = false) int limit) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		return result;
	}

}
