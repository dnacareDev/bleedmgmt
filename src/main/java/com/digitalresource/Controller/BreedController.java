package com.digitalresource.Controller;

import com.digitalresource.Entity.*;
import com.digitalresource.Service.*;
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

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

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

  @RequestMapping("/breed")
  public ModelAndView breed(ModelAndView mv, @RequestParam(value = "type") String type, @RequestParam(value = "id") int resource_id, @RequestParam(value = "crop_id", required = false) int crop_id) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Crop> crops = cropService.SearchCropList(type);

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
    Map<String, Object> result = new LinkedHashMap<String, Object>();
    int[] resourceNameId = resourceNameService.SelectResourceNameId(resource_name);
    int resourceId = 0;

    for (int i = 0; i < resourceNameId.length; i++) {
      if (resourceService.SearchResourceId(crop_id, resourceNameId[i]) != null) {
        resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i]);
        break;
      }
    }

    List<Detail> details = detailService.SelectDetailListByResource(resourceId);

    String crop_name = breedService.SearchCropName(crop_id);

    List<Breed> breed = breedService.SearchBreed2(crop_name, resourceId);

    List<StandardList> standardLists = new ArrayList<StandardList>();

    for(int i = 0; i < breed.size(); i++) {
      standardLists = breedService.SelectStandard(breed.get(i).getBreed_id());

      breed.get(i).setStandardList(standardLists);
    }

    result.put("breed", breed);
    result.put("detail", details);

//    Gson gson = new Gson();
//    String jsonString = gson.toJson(result.get("standardList"));
//
//    System.out.println(jsonString);
//    System.out.println("================================");

    return result;
  }

  @ResponseBody
  @RequestMapping("insertBreed2")
  public int insertBreed(@RequestParam(value = "data") String data, @RequestParam(value = "resource_id") int resource_id, @RequestParam(value = "crop_id") int crop_id, @RequestParam(value = "resource_name") String resource_name) {
    int result = 0;

    result = breedService.insertBreed(resource_id, data, crop_id, resource_name);

    return result;
  }

  @ResponseBody
  @RequestMapping("deleteBreed")
  public int deleteBreed(String breed_id) {
    int result = breedService.deleteBreed(breed_id);
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

  // 첨부파일 등록
  @RequestMapping("insertBreedFile")
  public ModelAndView InsertBreedFile(ModelAndView mv, @ModelAttribute BreedFile breed_file, @RequestParam("file") MultipartFile file, @RequestParam("type") String type, @RequestParam("resource_id") int resource_id, @RequestParam(value = "crop_id", required = false) int crop_id) throws IOException {
    String[] extension = file.getOriginalFilename().split("\\.");

    String file_name = fileController.ChangeFileName(extension[1]);
    String origin_file_name = file.getOriginalFilename();

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

    String str = URLEncoder.encode(type, "UTF-8");
    String url = "/breed?type=" + str + "&id=" + resource_id + "&crop_id=" + crop_id;

    mv.setViewName("redirect:" + url);

    return mv;
  }

  // 첨부파일 수정
  @RequestMapping("updateBreedFile")
  public ModelAndView UpdateBreedFile(ModelAndView mv, @ModelAttribute BreedFile breed_file, @RequestParam("file") MultipartFile file, @RequestParam("type") String type, @RequestParam("resource_id") int resource_id, @RequestParam(value = "crop_id", required = false) int crop_id) throws IOException {
    if (file.isEmpty()) {
      int update_file = breedService.UpdateBreedFile(breed_file);
    } else {
//      String delete_path = "upload/" + breed_file.getUploads_file();
      String delete_path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/" + breed_file.getUploads_file();
      File origin_file = new File(delete_path);

      if (origin_file.delete()) {
        String[] extension = file.getOriginalFilename().split("\\.");

        String file_name = fileController.ChangeFileName(extension[1]);
        String origin_file_name = file.getOriginalFilename();

        String path = "upload";

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
    }

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
  public int excelUpload(@RequestParam("excel_list") String excel_list, @RequestParam("resource_id") int resource_id) {

    JSONArray arr = new JSONArray(excel_list);

    List<StandardList> standards = new ArrayList<StandardList>();

    for (int i = 0; i < arr.length(); i++) {
      JSONArray item = arr.getJSONArray(i);

      int crop_id = Integer.parseInt(item.getString(0));

      String variety_name = breedService.SearchCropName(crop_id);

      Breed breed = new Breed();
      breed.setVariety_name(variety_name);
      breed.setResource_id(resource_id);

//			int breed_result = breedService.insertBreed(resource_id, data, crop_id, resource_name);
      int breed_result = breedService.InsertBreed(breed);

      List<Detail> detail = breedService.SelectDetailExcel(resource_id);

      for (int j = 0; j < detail.size(); j++) {
        StandardList standard = new StandardList();
        standard.setBreed_id(breed.getBreed_id());
        standard.setDetail_id(detail.get(j).getDetail_id());

        if (j < item.length()) {
          if(!item.isNull(j + 1)) {
            standard.setStandard_data((String) item.get(j + 1));
          } else {
            standard.setStandard_data(null);
          }
        } else {
          standard.setStandard_data(null);
        }

        standards.add(standard);
      }
    }

    breedService.InsertExcel(standards);

    return 1;
  }

  @ResponseBody
  @RequestMapping("selectBreedDateGroup")
  public Map<String, Object> SelectDateGroup(@RequestParam("resource_name") String resource_name) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Map<String, String>> dataGroup = datalistService.SelectDateGroup(resource_name);

    result.put("dataGroup", dataGroup);

    return result;
  }

  @ResponseBody
  @RequestMapping("insertBreedDataList")
  public DataList InsertDataList(@ModelAttribute DataList dataList, @RequestParam("listData") String listData) {
    JSONArray arr = new JSONArray(listData);

    JSONObject obj = arr.getJSONObject(0);

    int crop_id = Integer.parseInt(obj.getString("crop_id"));
    String variety_name = breedService.SearchCropName(crop_id);

    List<Breed> breed = breedService.SearchBreed(variety_name);

    for (int i = 0; i < breed.size(); i++) {
      if (Objects.equals(breed.get(i).getCreate_date().split(" ")[0], obj.getString("datalist_date"))) {
        dataList.setResource_name(obj.getString("resource_name"));
        dataList.setDatalist_date(obj.getString("datalist_date"));
        dataList.setBreed_id(breed.get(i).getBreed_id());
      } else {
        continue;
      }

      datalistService.InsertDataList(dataList);
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

    List<Integer> breed_id = datalistService.SelectTarget(datalist_date, resource_name);
    List<Detail> detail = breedService.SelectDetailExcel(resource_id);

    Map<Integer, Object> Breed = new LinkedHashMap<Integer, Object>();

    for (int i = 0; i < breed_id.size(); i++) {
      Breed.put(i, breedService.SelectBreedStandard(breed_id.get(i)));
    }

    result.put("breed", Breed);
    result.put("detail", detail);

    return result;
  }

  @ResponseBody
  @RequestMapping("updateBreed")
  public int UpdateBreed(@RequestParam("data") String data) {
    int result = 0;

    JSONArray arr = new JSONArray(data);

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
}
