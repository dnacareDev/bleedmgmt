package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.BreedFile;
import com.digitalresource.Entity.BreedFileSimple;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.DataList;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Entity.Uploads;
import com.digitalresource.Entity.User;
import com.digitalresource.Service.BreedService;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.DataListService;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.LogService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;

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

  @Autowired
  private DataListService datalistService;

  @Autowired
  private FileController fileController;
  
  @Autowired
  private LogService logService;

  @RequestMapping("/breed")
  public ModelAndView breed(Authentication auth, ModelAndView mv, @RequestParam(value = "type") String type, @RequestParam(value = "id") int resource_id, @RequestParam(value = "crop_id", required = false) int crop_id) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    List<Crop> crops = cropService.SearchCropList(user.getUser_type(), user.getUser_name(), type, group);
    
    mv.addObject("user", user);
    
    mv.addObject("crop_id", crop_id);
    mv.addObject("cropList", crops);
    mv.addObject("type", type);
    mv.addObject("resource_id", resource_id);
    mv.setViewName("genome/breed");

    return mv;
  }

  @ResponseBody
  @RequestMapping("searchHeader")
  public Map<String, Object> SearchBreed(Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    int[] resourceNameId = resourceNameService.SelectResourceNameId(resource_name, group);
    int resourceId = 0;

    for (int i = 0; i < resourceNameId.length; i++) {
      if (resourceService.SearchResourceId(crop_id, resourceNameId[i], group) != null) {
        resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i], group);
        break;
      }
    }
    System.out.println("resource_name : " + resource_name);
    System.out.println("resourceId : " + resourceId);

    List<Detail> details = detailService.SelectDetailListByResource(resourceId);

    String crop_name = breedService.SearchCropName(crop_id);

    List<Breed> breed = breedService.SearchBreed2(crop_name, resourceId);

    List<StandardList> standardLists = new ArrayList<StandardList>();

    for(int i = 0; i < breed.size(); i++) {
      standardLists = breedService.SelectStandard2(breed.get(i).getBreed_id());

      breed.get(i).setStandardList(standardLists);
    }

    List<BreedFileSimple> breed_file = breedService.IsMatchBreedFile();
    
    System.out.println("breed : " + breed);
    
    result.put("breed", breed);
    result.put("detail", details);
    result.put("resource_id", resourceId);
    result.put("breed_file", breed_file);

    return result;
  }

  @ResponseBody
  @RequestMapping("searchHeader2")
  public Map<String, Object> SearchBreed2(Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    int[] resourceNameId = resourceNameService.SelectResourceNameId(resource_name, group);
    int resourceId = 0;

    for (int i = 0; i < resourceNameId.length; i++) {
      if (resourceService.SearchResourceId(crop_id, resourceNameId[i], group) != null) {
        resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i], group);
        break;
      }
    }
    System.out.println("resource_name : " + resource_name);
    System.out.println("resourceId : " + resourceId);

    List<Detail> details = detailService.SelectDetailListByResource(resourceId);

    String crop_name = breedService.SearchCropName(crop_id);

    List<Breed> breed = breedService.SearchBreed2(crop_name, resourceId);
    
    int[] filledBreedId = breedService.SearchFilledBreed(crop_name, resourceId);
    
    //System.out.println("filled : " + Arrays.toString(filledBreedId));

    List<StandardList> standardLists = new ArrayList<StandardList>();
    //List<StandardList> standardListsFiltered = new ArrayList<StandardList>();
    
    /*
    for(int i = 0; i < breed.size(); i++) {
      standardLists = breedService.SelectStandard2(breed.get(i).getBreed_id());

      breed.get(i).setStandardList(standardLists);
    }
    */
    
    standardLists = breedService.DatabaseSelect(crop_name, resourceId);

    /*
    for(int i=0 ; i<breed.size() ; i++) {
    	System.out.println("i = " + i);
    	for(int j=0 ; j<standardLists.size() ; j++) {
    		System.out.println("j = " + j);
    		if(breed.get(i).getBreed_id() == standardLists.get(j).getBreed_id()) {
    			breed.get(i).setStandardList(standardLists);
    		}
    	}
    }
    */
    
    
    
    
    List<BreedFileSimple> breed_file = breedService.IsMatchBreedFile();
    
//    System.out.println("breed : " + breed);
    
    result.put("breed", breed);
    result.put("filledBreedId", filledBreedId);
    result.put("standardLists", standardLists);
    result.put("detail", details);
    result.put("resource_id", resourceId);
    result.put("breed_file", breed_file);

    return result;
  }
  
  @ResponseBody
  @RequestMapping("insertBreed2")
  public int insertBreed(Authentication auth, @RequestParam(value = "data") String data, @RequestParam(value = "resource_id") int resource_id, @RequestParam(value = "crop_id") int crop_id, @RequestParam(value = "resource_name") String resource_name, @RequestParam(value = "type_check") int type_check, @RequestParam(value = "log_contents") String log_contents) {
    int result = 0;

    System.out.println("log_contents"+log_contents);
    
    User user = (User) auth.getPrincipal();
    int group = user.getUser_group();

    System.out.println("data = " + data);

    result = breedService.insertBreed(resource_id, data, crop_id, resource_name, type_check, group);

    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    logService.RecordLog(userIdName, userName, log_contents);
    
    return result;
  }

  @ResponseBody
  @RequestMapping("deleteBreed")
  public int deleteBreed(Authentication auth, String breed_id, @RequestParam("log_contents") String log_contents) {
    
	User user = (User)auth.getPrincipal();
	
	int result = breedService.deleteBreed(breed_id);

    String[] str = breed_id.split(",");

    int[] arr_breed = Arrays.stream(str).mapToInt(Integer::parseInt).toArray();

    datalistService.DeleteList(arr_breed);
    
    // 삭제시 사용이력에 로그 기록
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    logService.RecordLog(userIdName, userName, log_contents);
    

    return result;
  }

  // 첨부 파일 조회
  @ResponseBody
  @RequestMapping("selectBreedFile")
  public Map<String, Object> SelectBreedFile(@RequestParam("breed_id") int breed_id, @RequestParam("file_type") int file_type) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<BreedFile> breed_file = breedService.SelectBreedFile(breed_id, file_type);

    result.put("breed_file", breed_file);

    return result;
  }
  
  // 첨부파일 리스트에서 삭제
  @ResponseBody
  @RequestMapping("DeleteBreedFile")
  public int DeleteFile(
		  				Authentication auth,
		  				@RequestParam("breed_file_id") int breed_file_id)
		  				/*@RequestParam("log_file_contents") String log_file_contents*/ {
	  int result = breedService.DeleteBreedFile(breed_file_id);
	  /*
	  User user = (User)auth.getPrincipal();
      String userIdName = user.getUser_username(); 
	  String userName = user.getUser_name();
      logService.RecordLog(userIdName, userName, log_file_contents);
	  */
	  return result;
  }

  // 첨부파일 등록
  @RequestMapping("insertBreedFile")
  public ModelAndView InsertBreedFile(
		  							Authentication auth,
		  							ModelAndView mv, 
		  							@ModelAttribute BreedFile breed_file, 
		  							@RequestParam("file") MultipartFile file,
		  							@RequestParam("log_file_contents") String log_file_contents,
		  							@RequestParam("type") String type, 
		  							@RequestParam("resource_id") int resource_id, 
		  							@RequestParam(value = "crop_id", required = false) int crop_id) throws IOException {
    
	
//	  System.out.println(log_file_contents);
	
	  
	String[] extension = file.getOriginalFilename().split("\\.");

    String file_name = fileController.ChangeFileName(extension[1]);
    String origin_file_name = file.getOriginalFilename();
    
//    System.out.println("breed_file : " + breed_file);

//    String path = "src/main/webapp/upload";
    String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

    File filePath = new File(path);

    if (!filePath.exists())
      filePath.mkdirs();

    Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
    Path targetLocation = fileLocation.resolve(file_name);

    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    int insert_file = breedService.InsertBreedFile(breed_file);

    Uploads upload = new Uploads();
    upload.setUploads_file(file_name);
    upload.setUploads_origin_file(origin_file_name);
    upload.setBreed_file_id(breed_file.getBreed_file_id());

    int insert_upload = breedService.InsertBreedUpload(upload);
    
    
    User user = (User)auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    logService.RecordLog(userIdName, userName, log_file_contents);

    String str = URLEncoder.encode(type, "UTF-8");
    String url = "/breed?type=" + str + "&id=" + resource_id + "&crop_id=" + crop_id;

    mv.setViewName("redirect:" + url);

    return mv;
  }

  // 첨부파일 수정
  @RequestMapping("updateBreedFile")
  public ModelAndView UpdateBreedFile(
		  							Authentication auth,
		  							ModelAndView mv, 
		  							@ModelAttribute BreedFile breed_file, 
		  							@RequestParam("file") MultipartFile file, 
		  							@RequestParam("type") String type, 
		  							@RequestParam("log_file_contents_modify") String log_file_contents_modify,
		  							@RequestParam("resource_id") int resource_id, 
		  							@RequestParam(value = "crop_id", required = false) int crop_id) throws IOException {
    
	  
//	  System.out.println(log_file_contents_modify);
	  
	  int update_file = breedService.UpdateBreedFile(breed_file);
	  
		String[] extension = file.getOriginalFilename().split("\\.");

		System.out.println("isFile : " + file.getOriginalFilename().length());
		
		if(file.getOriginalFilename().length() != 0) {
			String file_name = fileController.ChangeFileName(extension[1]);
		    String origin_file_name = file.getOriginalFilename();
		    
//		    System.out.println("breed_file : " + breed_file);

//		    String path = "src/main/webapp/upload";
		    String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

		    File filePath = new File(path);

		    if (!filePath.exists())
		      filePath.mkdirs();

		    Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
		    Path targetLocation = fileLocation.resolve(file_name);

		    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		    Uploads upload = new Uploads();
		    upload.setUploads_file(file_name);
		    upload.setUploads_origin_file(origin_file_name);
		    upload.setBreed_file_id(breed_file.getBreed_file_id());

		    int update_upload = breedService.UpdateBreedUpload(upload);
		} 
		
    
    User user = (User)auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    logService.RecordLog(userIdName, userName, log_file_contents_modify);
    
    
    String str = URLEncoder.encode(type, "UTF-8");
    String url = "/breed?type=" + str + "&id=" + resource_id + "&crop_id=" + crop_id;

    mv.setViewName("redirect:" + url);

    return mv;
  }

  @ResponseBody
  @RequestMapping("searchBreed")
  public Map<String, Object> SearchBreed(Authentication auth, @RequestParam("breed_name") String breed_name) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    User user = (User) auth.getPrincipal();

    int crop_id = Integer.parseInt(breed_name);

    String crop_name = breedService.SearchCropName(crop_id);
    List<Breed> breed = breedService.SearchBreed(crop_name);                  // 품종 작물별 컬럼 조회
//		List<Display> display = service.SelectDisplay(user.getUser_id(), breed_name);          // 사용자별 품종 표시항목 조회
//
//		result.put("detail", detail);
//		result.put("display", display);
    result.put("breed", breed);

    return result;
  }

  @ResponseBody
  @RequestMapping("excelBreed")
  public int excelUpload(Authentication auth, 
		  				@RequestParam("excel_list") String excel_list, 
		  				@RequestParam("resource_id") int resource_id, 
		  				@RequestParam("type_check") int type_check, 
		  				@RequestParam("user_group") int user_group, 
		  				@RequestParam("resource_name") String resource_name) {
    JSONArray arr = new JSONArray(excel_list);

    System.out.println("arr = " + arr);

    List<StandardList> standards = new ArrayList<StandardList>();

    for (int i = 1; i < arr.length(); i++) {						// 원인은 모르겠으나 arr.getJSONArray(0)이 빈 데이터. 따라서 i=1부터 시작
      JSONArray item = arr.getJSONArray(i);

      System.out.println("item = " + item);

      int crop_id = Integer.parseInt(item.getString(0));

      String variety_name = breedService.SearchCropName(crop_id);

      Breed breed = new Breed();
      breed.setVariety_name(variety_name);
      breed.setResource_id(resource_id);
      breed.setRow_file(type_check);
      breed.setUser_group(user_group);

      int breed_result = breedService.InsertBreed(breed);
      

      List<Detail> detail = breedService.SelectDetailExcel2(resource_id);

      int cnt = 1;

      for (int j = 0; j < detail.size(); j++) {
        StandardList standard = new StandardList();

        standard.setBreed_id(breed.getBreed_id());
        standard.setDetail_id(detail.get(j).getDetail_id());


        if(detail.get(j).getDetail_type() == 2) {

          if (cnt <= item.length()) {
            if(!item.isNull(cnt)) {
              standard.setStandard_data((String) item.get(cnt));
            } else {
              standard.setStandard_data(null);
            }
          } else {
            standard.setStandard_data(null);
          }

          cnt++;
        } else {
          standard.setStandard_data(null);
        }

        standards.add(standard);
      }
    }

    int result = breedService.InsertExcel(standards);
    
    User user = (User) auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    String log_contents = resource_name + " " + (arr.length() - 1) + "행 입력";

    logService.RecordLog(userIdName, userName, log_contents);
    
    return result;
//    return 0;
  }

  @ResponseBody
  @RequestMapping("selectBreedDateGroup")
  public Map<String, Object> SelectDateGroup(@RequestParam("resource_id") int resource_id, @RequestParam("resource_name") String resource_name) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Map<String, String>> dataGroup = datalistService.SelectDateGroup(resource_id, resource_name);

    result.put("dataGroup", dataGroup);

    return result;
  }

  @ResponseBody
  @RequestMapping("insertBreedDataList")
  public DataList InsertDataList(@ModelAttribute DataList dataList, @RequestParam("listData") String listData) {
    JSONArray arr = new JSONArray(listData);
    JSONObject obj = arr.getJSONObject(0);

    int crop_id = obj.getInt("crop_id");
    int group = obj.getInt("user_group");
    int[] resource_name_id = resourceNameService.SelectResourceNameId(obj.getString("resource_name"), group);

    int resource_id = 0;
    for(int i = 0; i < resource_name_id.length; i++) {
      if(resourceService.SearchResourceId(crop_id, resource_name_id[i], group) != null) {
        resource_id = resourceService.SearchResourceId(crop_id, resource_name_id[i], group);
      } else {
        continue;
      }
    }

    List<Integer> breed_id = datalistService.SelectTarget(obj.getString("datalist_date"), obj.getString("resource_name"), resource_id);
    List<Breed> breed = breedService.SearchBreed3(resource_id);
    
    for (int i = 0; i < breed.size(); i++) {
      if (Objects.equals(breed.get(i).getCreate_date().split(" ")[0], obj.getString("datalist_date"))) {
        dataList.setResource_id(resource_id);
        dataList.setResource_name(obj.getString("resource_name"));
        dataList.setDatalist_date(obj.getString("datalist_date"));
        dataList.setBreed_id(breed.get(i).getBreed_id());
        dataList.setUser_group(group);
      } else {
        continue;
      }

      if(!breed_id.contains(breed.get(i).getBreed_id())) {
        datalistService.InsertDataList(dataList);
      }
    }
    

    return dataList;
  }

  @ResponseBody
  @RequestMapping("selectBreedStandard")
  public List<StandardList> SelectBreedStandard(@RequestParam("breed_id") int breed_id) {
    List<StandardList> result = breedService.SelectBreedStandard2(breed_id);

    return result;
  }

  @ResponseBody
  @RequestMapping("searchTargetBreed")
  public Map<String, Object> SearchTarget(Authentication auth, @RequestParam("datalist_date") String datalist_date, @RequestParam("resource_name") String resource_name, @RequestParam("resource_id") int resource_id) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    User user = (User) auth.getPrincipal();

    List<Integer> breed_id = datalistService.SelectTarget(datalist_date, resource_name, resource_id);
    List<Detail> detail = breedService.SelectDetailExcel(resource_id);

    Map<Integer, Object> Breed = new LinkedHashMap<Integer, Object>();

    System.out.println(breed_id);
    
    for (int i = 0; i < breed_id.size(); i++) {
      Breed.put(i, breedService.SelectBreedStandard2(breed_id.get(i)));
    }

    result.put("breed", Breed);
    result.put("detail", detail);

    return result;
  }

  @ResponseBody
  @RequestMapping("updateBreed")
  public int UpdateBreed(Authentication auth, @RequestParam("data") String data, @RequestParam("resource_name") String resource_name) {
    int result = 0;

    JSONArray arr = new JSONArray(data);
    
    System.out.println("arr : " + arr);

    for (int i = 0; i < arr.length(); i++) {
      JSONObject item = arr.getJSONObject(i);

      int breed_id = item.getInt("breed_id");
      int detail_id = item.getInt("detail_id");
      String standard = item.getString("standard");

      if(standard.isEmpty()) {
        result = breedService.UpdateBreed(breed_id, detail_id, null);
      } else {
        result = breedService.UpdateBreed(breed_id, detail_id, standard);
      }
    }

    User user = (User) auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    String log_contents = resource_name + " 데이터 셀 " + arr.length() + "개 수정";
    
    logService.RecordLog(userIdName, userName, log_contents);
    
    return result;
  }
  
  @ResponseBody
  @RequestMapping("updateBreed2")
  public int UpdateBreed2(Authentication auth, 
		  				@RequestParam("data") String data) {
    int result = 0;


    
    JSONArray arr = new JSONArray(data);
    
    JSONObject item2 = arr.getJSONObject(0);
    String resource_name = item2.getString("resource_name");
    
    System.out.println("arr : " + arr);

    for (int i = 0; i < arr.length(); i++) {
      JSONObject item = arr.getJSONObject(i);

      int breed_id = item.getInt("breed_id");
      int detail_id = item.getInt("detail_id");
      String standard = item.getString("standard");
      

      if(standard.isEmpty()) {
        result = breedService.UpdateBreed(breed_id, detail_id, null);
      } else {
        result = breedService.UpdateBreed(breed_id, detail_id, standard);
      }
    }

    User user = (User) auth.getPrincipal();
    String userIdName = user.getUser_username(); 
	String userName = user.getUser_name();
    String log_contents = resource_name + " 데이터 셀 " + arr.length() + "개 수정";
    
    logService.RecordLog(userIdName, userName, log_contents);
    
    return result;
  }

  @RequestMapping("updateAllBreed")
  public ModelAndView UpdateAllBreed(ModelAndView mv, @RequestParam("breed_id") int breed_id, @RequestParam("detail_id") int[] detail_id, @RequestParam("standard_data") String[] standard_data) {
    int result = 0;

    List<StandardList> list = new ArrayList<StandardList>();

    StandardList item = new StandardList();

    for (int i = 0; i < detail_id.length; i++) {
      item = new StandardList();

      if (standard_data[i].equals("")) {
        item.setBreed_id(breed_id);
        item.setDetail_id(detail_id[i]);
        item.setStandard_data(null);

        list.add(item);
      } else {
        item.setBreed_id(breed_id);
        item.setDetail_id(detail_id[i]);
        item.setStandard_data(standard_data[i]);

        list.add(item);
      }
    }

    result = breedService.UpdateAllBreed(list);

    mv.setViewName("redirect:/breed");

    return mv;
  }
  
  // 2022-06-03 | 다운로드 양식에 필요한 column(detail) 조회
  @ResponseBody
  @RequestMapping("search_detail_by_id")
  public Map<String, Object> SearchDetailById(@RequestParam("resource_id") int resource_id) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Detail> details = detailService.SearchDetailById(resource_id);

    //System.out.println(details.get(0).getDetail_name());
    
    result.put("detail", details); 


    return result;
  }
  
  @ResponseBody
  @RequestMapping("searchCellValue")
  public Map<String, Object> searchCellValue(
//		  									@RequestParam("breed_id") int breed_id, 
		  									@RequestParam("cell_value") String cell_value,
		  									@RequestParam("crop_name") String crop_name,
		  									@RequestParam("resource_id") String resource_id) {
	  Map<String , Object> result = new LinkedHashMap<>();
	  
//	  System.out.println(breed_id + cell_value + crop_name + resource_id);
	  
	  List<StandardList> standardList = breedService.SearchStandardByCellValue(cell_value);
	  
	  System.out.println(standardList);
	  
	  result.put("standardList", standardList);
	  
	  return result;
  }
  
  @ResponseBody
  @RequestMapping("searchOnlyHeader")
  public Map<String, Object> SearchOnlyHeader(Authentication auth, @RequestParam("crop_id") int crop_id, @RequestParam("resource_id") int resource_id) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();
    Map<String, Object> result = new LinkedHashMap<String, Object>();



    List<Detail> details = detailService.SelectDetailListByResource(resource_id);

    String crop_name = breedService.SearchCropName(crop_id);

    List<Breed> breed = breedService.SearchBreed2(crop_name, resource_id);

    /*
    List<StandardList> standardLists = new ArrayList<StandardList>();

    for(int i = 0; i < breed.size(); i++) {
      standardLists = breedService.SelectStandard2(breed.get(i).getBreed_id());

      breed.get(i).setStandardList(standardLists);
    }
    */

    
    result.put("breed", breed);
    result.put("detail", details);
    result.put("resource_id", resource_id);

    return result;
  }
}
