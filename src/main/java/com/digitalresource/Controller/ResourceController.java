package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Service.CropService;
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
  private FileController fileController;

  @RequestMapping("management-resource")
  public ModelAndView manageResource(ModelAndView mv) {
    mv.setViewName("resource/data_resource");
    return mv;
  }

  @RequestMapping("regist-resource")
  public ModelAndView registResourcePage(ModelAndView mv) {
    List<Crop> cropList = cropService.selectCropList();

    mv.addObject("cropList", cropList);

    mv.setViewName("resource/regist_resource");
    return mv;
  }

  @PostMapping("search-resource")
  @ResponseBody
  public Map<String, Object> searchResource(@RequestParam("page_num") int page_num, @RequestParam("limit") int limit) {
    Map<String, Object> map = new HashMap<>();
    List<ResourceList> resourceList = RService.searchResource();
    int count = RService.selectResourceCount();
    int offset = (page_num - 1) * limit;
    int end_page = (count + limit - 1) / limit;
    map.put("resourceList", resourceList);
    map.put("page_num", page_num);
    map.put("end_page", end_page);
    map.put("offset", offset);
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
  public int confirmResourceName(@RequestParam("crop_id") int crop_id, @RequestParam("resource_name") String resource_name) {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("crop_id", crop_id);
    param.put("resource_name", resource_name);
    int result = RNService.confirmResourceName(param);
    System.out.println(result);
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
  public int registerResource(MultipartFile inputFile, MultipartFile charFile,
                              @RequestParam(value = "crop_id", required = false) String crop_id, @RequestParam("resource_name") String resource_name,
                              @RequestParam(value = "detail_list") String detail_list,
                              @RequestParam(value = "feature_group", required = false) String feature_group,
                              @RequestParam(value = "detail_count") int detail_count) throws IOException {
    Map<String, Object> param = new HashMap<String, Object>();
    ResourceName resourceName = new ResourceName();
    resourceName.setResource_name(resource_name);
    // insert resource_name
    int resource_id = RNService.insertResource_name(resourceName);
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
      String path = "/upload";

      File filePath = new File(path);
      if (!filePath.exists())
        filePath.mkdirs();
      Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
      Path targetLocation = fileLocation.resolve(inputFile_name);

      Files.copy(inputFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    if (charFile != null) {
      String[] extension = charFile.getOriginalFilename().split("\\.");

      charFile_name = fileController.ChangeFileName(extension[1]);
      String origin_charFile_name = charFile.getOriginalFilename();
      String path = "/upload";

      File filePath = new File(path);
      if (!filePath.exists())
        filePath.mkdirs();
      Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
      Path targetLocation = fileLocation.resolve(charFile_name);

      Files.copy(charFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    Resource resource = new Resource();
    resource.setResource_template(inputFile_name);
    resource.setResource_character_template_file(charFile_name);
    resource.setCrop_id(Integer.parseInt(crop_id));
    resource.setResource_id(resource_id);
    resource.setDetailCount(detail_count);
    resource.setTrait_id(feature_group);
    resource.setDetail_count(detail_count);
    resource.setResource_name_id(resourceName.getResource_name_id());
    resource.setDetailList(detail_list);

    int result = RService.insertResource(resource);

    return result;
  }


  @ResponseBody
  @RequestMapping("resourceDownloadFile")
  public ResponseEntity<Object> resourceDownloadFile(@RequestParam(value = "file_name") String file_name) {
    String path = "/upload/" + file_name;

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
  @RequestMapping("change-resource-use")
  public int changeResourceUse(@RequestParam("use_name") String use_name, @RequestParam("limit") int limit) {
    int result = 0;
    int toggle = 0;
    Map<String, Object> map = new HashMap<String, Object>();
    if (use_name.equals("사용")) {
      use_name = "1";
      toggle = 1;
    } else if (use_name.equals("비사용")) {
      use_name = "0";
      toggle = 1;
    }

    if (toggle == 1) {
      map.put("use_name", use_name);
      map.put("limit", limit);
      result = RService.changeResourceUse(map);
    } else {
      result = 0;
    }
    return result;
  }

  @ResponseBody
  @RequestMapping("deleteResource")
  public int deleteResource(Resource resource) {
    int result = 0;
    result = RService.deleteResource(resource);
    return result;
  }

  @ResponseBody
  @RequestMapping("resource-list")
  public ResponseEntity<?> resourceList() {
    Map<String, Object> map = new HashMap<String, Object>();
    List<ResourceName> resourceList = RService.resourceList();
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
}