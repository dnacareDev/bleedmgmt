package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Service.Cropservice;
import com.digitalresource.Service.ResourceNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
