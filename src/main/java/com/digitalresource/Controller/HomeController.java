package com.digitalresource.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.ChartCount;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.MonthCount;
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Entity.User;
import com.digitalresource.Entity.YearCount;
import com.digitalresource.Service.BreedService;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.ResourceService;

@Controller
public class HomeController {

  @Autowired
  private DetailService detailService;

  @Autowired
  private ResourceService RService;

  @Autowired
  private CropService cropService;

  @Autowired
  private BreedService breedService;

  @RequestMapping("/home")
  public ModelAndView gethome(ModelAndView mv, Authentication auth) {
    
	  
	  
	User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    List<Crop> crops = cropService.selectCropList();
    List<ResourceName> resourceList = RService.resourceList(group);

    mv.addObject("cropList", crops);
    mv.addObject("resourceList", resourceList);

    mv.setViewName("home/resource_home");

    return mv;
  }

  @ResponseBody
  @RequestMapping("selectFile")
  public Map<String, Object> SelectFile(Authentication auth) {
    User user = (User)auth.getPrincipal();
    int user_group = user.getUser_group();

    Map<String, Object> result = new LinkedHashMap<String, Object>();

    ChartCount file = detailService.SelectFileCount(user_group);

    result.put("file", file);

    return result;
  }

  @ResponseBody
  @RequestMapping("selectCrop")
  public Map<String, Object> SelectCrop(Authentication auth) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    Map<String, Object> result = new LinkedHashMap<String, Object>();
    List<ResourceName> resourceList = RService.resourceList(group);
    List<Crop> crops = cropService.selectCropList();
    int[][] count = new int[crops.size()][resourceList.size()];

    for (int i = 0; i < crops.size(); i++) {
      String crop_name = crops.get(i).getCrop_name();

      for (int j = 0; j < resourceList.size(); j++) {
        String resource_name = resourceList.get(j).getResource_name();

        count[i][j] = RService.SelectCropCount(resource_name, crop_name, group);
      }
    }

    result.put("resourceList", resourceList);
    result.put("cropList", crops);
    result.put("count", count);

    return result;
  }

  @ResponseBody
  @RequestMapping("selectMonth")
  public Map<String, Object> SelectMonth(Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("year") int year) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    Map<String, Object> result = new LinkedHashMap<String, Object>();
    MonthCount monthCounts = new MonthCount();

    List<ResourceName> resourceList = RService.resourceList(group);
    
//    List<ResourceList> resourceList = RService.searchResource(group);

    String crop_name = breedService.SearchCropName(crop_id);

    int[][] count = new int[resourceList.size()][12];

    for (int i = 0; i < resourceList.size(); i++) {
      String resource_name = resourceList.get(i).getResource_name();

      for (int j = 1; j <= 12; j++) {
        String month = String.format("%02d", j);

//        monthCounts = RService.SelectCropMonth(crop_name, month, resource_name, group);
        monthCounts = RService.SelectCropMonth(crop_name, month, year, resource_name, group);

        if(monthCounts != null) {
          count[i][j - 1] = monthCounts.getResource_count();
        } else {
          count[i][j - 1] = 0;
        }
      }
    }

    result.put("resourceList", resourceList);
    result.put("count", count);

    return result;
  }
  

  
}
