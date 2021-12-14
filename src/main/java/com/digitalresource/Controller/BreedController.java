package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Service.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

	@Autowired
	private ResourceNameService resourceNameService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private DetailService detailService;
	
	@Autowired
	private BreedService breedService;
	
	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv, @RequestParam(value="type") String type,@RequestParam(value="id") int resource_id) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		List<Crop> crops = cropService.SearchCropList(type);

		mv.addObject("cropList", crops);
		mv.addObject("type", type);
		mv.addObject("resource_id", resource_id);
		mv.setViewName("genome/breed");
		
		return mv;
	}

	@ResponseBody
	@RequestMapping("searchHeader")
	public Map<String, Object> SearchBreed(ModelAndView mv,Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		int[] resourceNameId = resourceNameService.SelectResourceNameId(resource_name);
		int resourceId = 0;

		for(int i = 0; i < resourceNameId.length; i++) {
			if(resourceService.SearchResourceId(crop_id, resourceNameId[i]) != null) {
				resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i]);
				break;
			}
		}
		mv.setView(null);
		List<Detail> details = detailService.SelectDetailListByResource(resourceId);	
		List<Map<String, Object>> standardList = breedService.selectStandard(resourceId);
		result.put("standardList",standardList);
		result.put("detail", details);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("updateStandardCell")
	public int updateStandardCell(@RequestParam(value="standard_data") String standard_data,@RequestParam(value="standard_id") int standard_id) {
		int result = 0;
		
		StandardList standard = new StandardList();
		standard.setStandard_data(standard_data);
		standard.setStandard_id(standard_id);
		result = breedService.updateStandardCell(standard);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("insertBreed2")
	public int insertBreed(@RequestParam(value="data")String data, @RequestParam(value="resource_id") int resource_id) {
		int result = 0;
		
		result = breedService.insertBreed(resource_id,data);

		return result;
	}

	@ResponseBody
	@RequestMapping("selectBreedDateGroup")
	public Map<String, Object> SelectDateGroup(@RequestParam("datalist_type") String datalist_type) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

//		List<Map<String, String>> dataGroup = d_service.SelectDateGroup(datalist_type);
//
//		result.put("dataGroup", dataGroup);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("deleteBreed")
	public int deleteBreed(String breed_id) {
		int result = breedService.deleteBreed(breed_id);
		return result;
	}
}
