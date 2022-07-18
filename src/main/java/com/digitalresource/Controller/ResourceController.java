package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.Feature;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceAllList;
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Entity.User;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.LogService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;

@Controller
public class ResourceController {
  @Autowired
  private CropService cropService;

  @Autowired
  private ResourceNameService RNService;

  @Autowired
  private ResourceService RService;
  
  @Autowired
  private LogService logService;

  @Autowired
  private FileController fileController;

  @RequestMapping("management-resource")
  public ModelAndView manageResource(ModelAndView mv) {
    mv.setViewName("resource/data_resource");
    return mv;
  }

  @RequestMapping("regist-resource")
  public ModelAndView registResourcePage(ModelAndView mv, Authentication auth) {
//    List<Crop> cropList = cropService.selectCropList();
	  List<Crop> cropList = cropService.searchAllCrops();  
      User user = (User)auth.getPrincipal();
    
      mv.addObject("user", user);
      mv.addObject("cropList", cropList);

      mv.setViewName("resource/regist_resource");
      return mv;
  }

  @PostMapping("search-resource")
  @ResponseBody
  public Map<String, Object> searchResource(@RequestParam("page_num") int page_num, @RequestParam("limit") int limit, @RequestParam("user_group") int user_group) {
    Map<String, Object> map = new HashMap<>();
    List<ResourceList> resourceList = RService.searchResource(user_group);
    int count = RService.selectResourceCount(user_group);
    int offset = (page_num - 1) * limit;
    int end_page = (count + limit - 1) / limit;
    map.put("resourceList", resourceList);
    map.put("page_num", page_num);
    map.put("end_page", end_page);
    map.put("offset", offset);
    return map;
  }
  
  /*
  @PostMapping("search-resource2")
  @ResponseBody
  public Map<String, Object> searchResource2(Authentication auth) {
    Map<String, Object> map = new HashMap<>();
    
    User user = (User)auth.getPrincipal();
    
    List<ResourceList> resourceList = RService.searchResource(user_group);
    int count = RService.selectResourceCount(user_group);
    map.put("resourceList", resourceList);
    return map;
  }
  */
  
  
  // 2022-07-04 | crop_id가 일치하는 모든 resource_id를 조회
  @ResponseBody
  @RequestMapping("searchResourceAll")
  public Map<String, Object> searchResourceAll(@RequestParam("crop_id") int crop_id) {
	  Map<String, Object> map = new HashMap<>();
	  
	  System.out.println("crop_id : " + crop_id);
	  
	  List<ResourceAllList> resourceAllList = RService.searchResourceAll(crop_id);
	  
	  map.put("resourceAllList", resourceAllList);
	  
	  return map;
  }
  

  @ResponseBody
  @RequestMapping("nameOverlapCheck")
  public int NameOverlapCheck(@RequestParam("resource_name") String resource_name) {

    int result = RNService.getCountResourceNameByCrop(resource_name);

    return result;
  }

  @ResponseBody
  @RequestMapping("confirmResourceName")
  public int confirmResourceName(@RequestParam("crop_id") int crop_id, 
		  						@RequestParam("resource_name") String resource_name,
		  						@RequestParam("new_crop") String new_crop,
		  						@RequestParam("group") int group) {
    Map<String, Object> param = new HashMap<String, Object>();
    
    System.out.println("crop_id : " + crop_id);
    System.out.println("resource_name : " + resource_name);
    System.out.println("new_crop : " + new_crop);
    System.out.println("group : " + group);
    
    
    param.put("crop_id", crop_id);
    param.put("resource_name", resource_name);
    param.put("user_group", group);
    int result = RNService.confirmResourceName(param);
    System.out.println(result);
    
    if(crop_id == -1 && new_crop == "") {
    	return -1;
    } else if(resource_name == "") {
    	return -2;
    }
    
    return result;
    
  }

  @ResponseBody
  @RequestMapping("featureHeadList")
  public List<Feature> featureHeadList(@RequestParam("feature_group") int feature_group) {
    List<Feature> result = RNService.featureHeadList(feature_group);
    return result;
  }

  @ResponseBody
  @RequestMapping("registerResource")
  public int registerResource(Authentication auth, MultipartFile inputFile, MultipartFile charFile1, @RequestParam(value = "charFile2", required = false) String charFile2,
                              //@RequestParam(value = "crop_id", required = false) String crop_id,
                              @RequestParam(value = "crop_id", required = false) int crop_id,
                              @RequestParam("new_crop") String new_crop,
                              @RequestParam("resource_name") String resource_name,
                              @RequestParam(value = "detail_list") String detail_list,
                              @RequestParam(value = "feature_group", required = false) String feature_group,
                              @RequestParam(value = "detail_count") int detail_count,
                              @RequestParam("user_group") int group,
                              @RequestParam("log_contents") String log_contents) throws IOException {
    Map<String, Object> param = new HashMap<String, Object>();
    ResourceName resourceName = new ResourceName();
    
    System.out.println("=================================");
    System.out.println("registerResource Controller Start");
    System.out.println("=================================");
    System.out.println("crop_id : " + crop_id);
    System.out.println("new_crop : " + new_crop);
    System.out.println("resource_name : " + resource_name);
    System.out.println("detail_list : " + detail_list);
    System.out.println("feature_group : " + feature_group);
    System.out.println("detail_count : " + detail_count);
    
    
    resourceName.setResource_name(resource_name);
    resourceName.setUser_group(group);
    
    System.out.println("resourceName : " + resourceName);
    System.out.println();
    
    // insert resource_name
    int resource_id = RNService.insertResource_name(resourceName);
    
    Crop crop = new Crop();
    
    System.out.println("crop before : " + crop);
    
    if(crop_id == -1) {
    	crop.setCrop_name(new_crop);
    	crop_id = cropService.registCrop(crop);
    }
    
    System.out.println("crop after : " + crop);
    /* 파일 업로드는 이제 필요없으므로 주석화
    // 파일 업로드 및 파일 이름 저장
    String inputFile_name = "";
    String charFile_name = "";
    if (feature_group.equals("-1")) {
      feature_group = "";
    }
    // 입력 항목 파일 파일 저장
    if (inputFile != null) {
      String[] extension = inputFile.getOriginalFilename().split("\\.");

      inputFile_name = fileController.ChangeFileName(extension[1]);
      String origin_inputFile_name = inputFile.getOriginalFilename();
      String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

      File filePath = new File(path);
      if (!filePath.exists())
        filePath.mkdirs();
      Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
      Path targetLocation = fileLocation.resolve(inputFile_name);

      Files.copy(inputFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
    if (charFile1 != null) {
      String[] extension = charFile1.getOriginalFilename().split("\\.");

      charFile_name = fileController.ChangeFileName(extension[1]);
      String origin_charFile_name = charFile1.getOriginalFilename();
      String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

      File filePath = new File(path);
      if (!filePath.exists())
        filePath.mkdirs();
      Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
      Path targetLocation = fileLocation.resolve(charFile_name);

      Files.copy(charFile1.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
    

    Resource resource = new Resource();
    resource.setResource_template(inputFile_name);
    if(charFile2 != null) {
      resource.setResource_character_template_file(charFile2);
    } else {
      resource.setResource_character_template_file(charFile_name);
    }
    */
    
    Resource resource = new Resource();
    
    //resource.setCrop_id(Integer.parseInt(crop_id));    
    //resource.setCrop_id(crop_id);
    //resource.setCrop_id(crop.getCrop_id());
    
    // 신규작물(crop_id = -1) 이면 Crop table(PK, AI)에서 받은 id값을, 기존작물이면 javascript에서 받은 id값을 쓴다
    if(crop.getCrop_id() == 0) {
    	resource.setCrop_id(crop_id);
    } else {
    	resource.setCrop_id(crop.getCrop_id());
    }
    
    resource.setResource_id(resource_id);
    resource.setDetailCount(detail_count);
    resource.setTrait_id(feature_group);
    resource.setDetail_count(detail_count);
    resource.setResource_name_id(resourceName.getResource_name_id());
    resource.setDetailList(detail_list);
    resource.setUser_group(group);

    System.out.println("resource : " + resource);
    
    int result = RService.insertResource(resource);

    //로그 컨텐츠
    User user = (User) auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    logService.RecordLog(userIdName, userName, log_contents);
    
    return result;
    

  }

  @ResponseBody
  @RequestMapping("resourceDownloadFile")
  public ResponseEntity<Object> resourceDownloadFile(@RequestParam(value = "file_name") String file_name) {
    String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/" + file_name;

    try {
      Path filePath = Paths.get(path);
      InputStreamResource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

      File file = new File(path);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

      return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
    }
  }

  @ResponseBody
  @RequestMapping("changeResourceUse")
  public int changeResourceUse(@RequestParam("resource_id") String resource_id) {
    int result = 0;

    JSONArray arr = new JSONArray(resource_id);

    for(int i = 0; i < arr.length(); i++) {
      int resourceId = arr.getInt(i);
      System.out.println("resourceId = " + resourceId);
      int using = RService.SelectResourceUse(resourceId);
      System.out.println("before using = " + using);
      if(using == 1) {
        result = RService.UpdateResourceUse(0, resourceId);
        System.out.println("result = " + result);
        System.out.println("미사용");
        System.out.println("after using = " + RService.SelectResourceUse(resourceId));
      } else if(using == 0){
        result = RService.UpdateResourceUse(1, resourceId);
        System.out.println("result = " + result);
        System.out.println("사용");
        System.out.println("after using = " + RService.SelectResourceUse(resourceId));
      }
    }

    return result;
  }

  @ResponseBody
  @RequestMapping("deleteResource")
  public int deleteResource(@RequestParam("resource_id") String resource_id) {
	    int result = 0;

	    JSONArray arr = new JSONArray(resource_id);
	    System.out.println("arr : " + arr);

	    
	    for(int i=0 ; i<arr.length() ; i++) {
	    	int resourceId = arr.getInt(i);
//	    	System.out.println("resourceId : " + resourceId);
	    	RService.deleteResourceNameById(resourceId);		// resourceId와 일치하는 resource_name 데이터 삭제
	    	RService.deleteBreedById(resourceId);				// resourceId와 일치하는 breed_id 데이터 삭제
	    	
	    	
	    	
	    	int crop_id = RService.SelectCropIdByResourceId(resourceId);
    		System.out.println("crop_id : " + crop_id);
    		
    		int restCropCount = RService.searchResourceCount(crop_id);
	    	System.out.println("restCropCount : " + restCropCount);
    		
	    	
	    	if(restCropCount == 1) {
	    		System.out.println("only one crop");
	    		
	    		RService.deleteResourceById(resourceId);
	    		System.out.println("aaa");
	    		cropService.deleteCrop(crop_id);
	    		System.out.println("bbb");
	    	} else {
	    		RService.deleteResourceById(resourceId);
	    	}
	    	
	    				// resourceId에 해당하는 resource 데이터 삭제
	    }
	    
	    
	    
	    
	    
	    
	    // 2022-06-07 | 삭제기능 일단 구현. 현재 자원관리 출력 및 필터링에 문제가 있어 보류 
	    /*
	    for(int i = 0; i < arr.length(); i++) {
		      int resourceId = arr.getInt(i);
		      result = RService.UpdateResourceUse(0, resourceId);
		      result = RService.UpdateDeleteCheck(resourceId);
		      System.out.println("after delete = " + RService.SelectResourceUse(resourceId));
	    }
	    

	    return result;
	    */
	    return result;
	  }
  
  

  @ResponseBody
  @RequestMapping("resource-list")
  public ResponseEntity<?> resourceList(Authentication auth) {
    Map<String, Object> map = new HashMap<String, Object>();
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    List<ResourceName> resourceList = RService.resourceList(group);

    map.put("resourceList", resourceList);
    return ResponseEntity.ok(map);
  }

  @ResponseBody
  @RequestMapping("detail-head")
  public ResponseEntity<?> selectDetailHead(@RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("crop_id", crop_id);
    param.put("resource_name", resource_name);
    List<Detail> detailList = RService.selectDetailHead(param);
    map.put("detailList", detailList);
//    System.out.println(map);
    return ResponseEntity.ok(map);
  }

  @RequestMapping("detail-display")
  public String detailDisplayAction(
      @RequestParam(value = "detail_id", required = false) String[] detailList,
      @RequestParam(value = "detail_id_checked", required = false) String[] detailCheckList,
      ModelAndView mv,
      @RequestParam("resource_name_re") String resoruce_name,
      @RequestParam("crop_id_re") String crop_id,
      @RequestParam("resource_id_re") String resource_id,
      RedirectAttributes redirectAttributes
  ) throws IOException {
    Map<String, Object> map = new HashMap<String, Object>();
    Map<String, Object> param = new HashMap<String, Object>();
    Map<String, Object> basic = new HashMap<String, Object>();
    Map<String, Object> seed = new HashMap<String, Object>();
    Map<String, Object> company = new HashMap<String, Object>();
    int result = 0;

    Map<String, Object> detailIdArr = new HashMap<String, Object>(); // 표시 되는 것
    Map<String, Object> detailIdArr2 = new HashMap<String, Object>(); // 표시 안되는 것

    if (detailList != null) {
      for (int i = 0; i < detailList.length; i++) {
        detailIdArr = RService.SelectDetailInfo(detailList[i]);

        if (detailIdArr.get("detail_info").equals(0)) {
          basic.put("detail", detailList[i]);
          basic.put("detail_display", 1);

          RService.detailDisplayAction(basic);
        } else if (detailIdArr.get("detail_info").equals(1)) {
          company.put("detail", detailList[i]);
          company.put("detail_display", 1);

          RService.detailDisplayAction(company);
        } else if (detailIdArr.get("detail_info").equals(2)) {
          seed.put("detail", detailList[i]);
          seed.put("detail_display", 1);

          RService.detailDisplayAction(seed);
        }
      }
    }

    if (detailCheckList != null) {
      for (int i = 0; i < detailCheckList.length; i++) {
        detailIdArr2 = RService.SelectDetailInfo(detailCheckList[i]);

        if (detailIdArr2.get("detail_info").equals(0)) {
          basic.put("detail", detailCheckList[i]);
          basic.put("detail_display", 0);

          RService.detailDisplayAction(basic);
        } else if (detailIdArr2.get("detail_info").equals(1)) {
          company.put("detail", detailCheckList[i]);
          company.put("detail_display", 0);

          RService.detailDisplayAction(company);
        } else if (detailIdArr2.get("detail_info").equals(2)) {
          seed.put("detail", detailCheckList[i]);
          seed.put("detail_display", 0);

          RService.detailDisplayAction(seed);
        }
      }
    }

    redirectAttributes.addAttribute("type", resoruce_name);
    redirectAttributes.addAttribute("id", resource_id);
    redirectAttributes.addAttribute("crop_id", crop_id);

    return "redirect:/breed?type={type}&id={id}&crop_id={crop_id}";
  }
  
  // 2022-06-02 | resource_template 데이터 조회
  @RequestMapping("select_resource_by_id")
  @ResponseBody
  public Resource selectResourceById(@RequestParam("resource_id") int resource_id) {
	Resource result = new Resource();
    result = RService.selectResourceById(resource_id);
    return result;
  }
  
}