package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.User;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@Autowired
	private ResourceNameService resourceNameService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private DetailService detailService;
	
	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv,@RequestParam(value="type")String type) {
		List<Crop> cropList = cropService.selectCropList();
		mv.addObject("cropList", cropList);
		mv.addObject("type", type);
		mv.setViewName("genome/breed");
		
		return mv;
	}

	@ResponseBody
	@RequestMapping("searchHeader")
	public Map<String, Object> SearchBreed(Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		int[] resourceNameId = resourceNameService.SelectResourceNameId(resource_name);

		int resourceId = 0;

		for(int i = 0; i < resourceNameId.length; i++) {
			if(resourceService.SearchResourceId(crop_id, resourceNameId[i]) != null) {
				resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i]);
				break;
			}
		}

		List<Detail> details = detailService.SelectDetailListByResource(resourceId);

		result.put("detail", details);

		return result;
	}

}
