package com.digitalresource.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Feature;
import com.digitalresource.Service.Cropservice;
import com.digitalresource.Service.ResourceNameService;

@Controller
public class ResourceController {
    @Autowired
    private Cropservice cropService;

    @Autowired
    private ResourceNameService RNService;

    @RequestMapping("management-resource")
    public ModelAndView manageResource(ModelAndView mv) {
        mv.setViewName("resource/data_resource");
        return mv;
    }

    @RequestMapping("regist-resource")
    public ModelAndView registResourcePage(ModelAndView mv) {
        List<Crop> cropList = cropService.selectCropList();

        mv.addObject("cropList",cropList);

        mv.setViewName("resource/regist_resource");
        return mv;
    }

    @PostMapping("search-resource")
    @ResponseBody
    public Map<String, Object> searchResource(@RequestParam("sample_name") String sample_name, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit){
        Map<String,Object> map = new HashMap<>();
        return map;
    }

    @ResponseBody
    @RequestMapping("nameOverlapCheck")
    public int NameOverlapCheck(@RequestParam("resource_name") String resource_name) {

        System.out.println(resource_name);

        int result = RNService.getCountResourceNameByCrop(resource_name);

        return result;
    }
    
    @ResponseBody
    @RequestMapping("confirmResourceName")
    public int confirmResourceName(@RequestParam("crop_id") int crop_id, @RequestParam("resource_name")String resource_name) {
    	Map<String,Object> param = new HashMap<String, Object>();
    	int result = RNService.confirmResourceName(param);
    	return result;
    }
    
    @ResponseBody
    @RequestMapping("featureHeadList")
    public List<Feature> featureHeadList(@RequestParam("feature_group")int feature_group){
    	List<Feature> result = RNService.featureHeadList(feature_group);
    	return result;
    }
    
    @ResponseBody
    @RequestMapping("registerResource")
    public int registerResource (MultipartFile inputFile, MultipartFile charFile,
    								@RequestParam(value="crop_id", required=false) int crop_id, @RequestParam("resource_name") String resource_name) {
    	System.out.println(inputFile);
    	System.out.println(charFile);
    	System.out.println(crop_id);
    	System.out.println(resource_name);
    	// insert resource_name
    	int resource_id = RNService.insertResource_name(resource_name); 
    	System.out.println(resource_id);
    	// 파일 업로드 및 파일 이름 저장
    	// insert resource
    	int result = 1;
    	return result;
    }
    
   
}
