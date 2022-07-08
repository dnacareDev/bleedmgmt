package com.digitalresource.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Entity.User;
import com.digitalresource.RModule.RunCorrlation;
import com.digitalresource.RModule.RunTrait;
import com.digitalresource.Service.AnalysisService;
import com.digitalresource.Service.BreedService;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;

@Controller
public class AnalysisController {

  @Autowired
  private BreedService breedService;

  @Autowired
  private CropService cropService;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private ResourceNameService resourceNameService;

  @Autowired
  private AnalysisService service;

  // 통합 분석 페이지
  @RequestMapping("/analysis")
  public ModelAndView Analysis(Authentication auth, ModelAndView mv) {
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    //String type = "파종대장";
    
//    int crop_id = service.SelectCropIdByName(crop);

//    List<Crop> crops = cropService.SearchCropList(resource_name, group);
    
//    System.out.println("resource_name(mv) : " + resource_name);
//    System.out.println("crop_id(mv) : " + crop_id);
//    System.out.println("cropList(mv) : " + crops.get(0).getCrop_id());
    
//    mv.addObject("crop_id", crop_id);
//    mv.addObject("cropList", crops);
    mv.setViewName("lab/analysis");

    return mv;
  }

  

  // 작목 조회
  @ResponseBody
  @RequestMapping("/selectTarget")
  public Map<String, Object> SelectTarget(
//		  									Authentication auth, 
		  									@RequestParam("crop_name") String crop_name, 
		  									@RequestParam(required = false, value = "total_id") int[] total_id,
//		  									@RequestParam("resource_id") int resource_id,
		  									@RequestParam("first_column") String first_column,
		  									@RequestParam("type") int type) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Breed> breed = new ArrayList<Breed>();
//    User user = (User)auth.getPrincipal();
//    int group = user.getUser_group();

//    int crop_id = service.SelectCropIdByName(crop_name);
    
//    String resourceName = "파종대장";

//    int[] resource_name_id = resourceNameService.SelectResourceNameId(resource_name, group);
    
//    System.out.println("resource_name_id : " + Arrays.toString(resource_name_id));

//    int[] resource_id = new int[resource_name_id.length];
    
//    int selected_resource_id = 0;
/*
    for (int i = 0; i < resource_name_id.length; i++) {
      if (resourceService.SearchResourceId(crop_id, resource_name_id[i], group) != null) {
        resource_id[i] = resourceService.SearchResourceId(crop_id, resource_name_id[i], group);
        
        if(resource_id[i] != 0) {
        	selected_resource_id = resource_id[i];
        	break;
        }
      }
    }
  */  
//    System.out.println("resource_id : " + resource_id);
    
//    System.out.println("selected_resource_id : " + selected_resource_id);

//	1도 2도 작동을 안함. 아예 SelectBreed3이라는 새로운 쿼리문을 만드는것도 방법    
//  breed = service.SelectBreed(crop_name, resource_id, type);
    breed = service.SelectBreed3(crop_name, total_id, first_column, type);
    
//    System.out.println("breed : " + breed);

    result.put("breed", breed);

    return result;
  }

  // 작목 조회
  @ResponseBody
  @RequestMapping("/selectTarget1")
  public Map<String, Object> SelectTarget1(Authentication auth, @RequestParam("name") String name, @RequestParam(required = false, value = "total_id") int[] total_id, @RequestParam("type") int type) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Breed> breed = new ArrayList<Breed>();
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

    int crop_id = service.SelectCropIdByName(name);

    Breed breedById = breedService.SearchBreedById(total_id[0]);
    int resource_id = breedById.getResource_id();

    breed = service.SelectBreed2(name, resource_id, type);

    result.put("breed", breed);

    return result;
  }

  // 분석형질 조회
  @ResponseBody
  @RequestMapping("selectTrait")
  public List<Detail> SelectTrait(@RequestParam("detail_name") String detail_name, @RequestParam("detail_type") int detail_type) {
    List<Detail> result = service.selectTrait(detail_name, detail_type);

    return result;
  }

  @ResponseBody
  @RequestMapping("selectTrait1")
  public List<Detail> SelectTrait1(
		  							Authentication auth, 
		  							@RequestParam("crop_name") String crop_name, 
		  							@RequestParam(required = false, value = "total_id") int[] total_id,
  									@RequestParam("resource_id") int resource_id){
    List<Detail> result = new ArrayList<>();
    User user = (User)auth.getPrincipal();
    int group = user.getUser_group();

//    String resourceName = "파종대장";
//
//    int[] resource_name_id = resourceNameService.SelectResourceNameId(resourceName, group);
//
//    System.out.println("resource_name_id(selectTrait1) = " + Arrays.toString(resource_name_id));
//
//    for (int i = 0; i < resource_name_id.length; i++) {
//
//      int resource_id = 0;
//
//      if (resourceService.SearchResourceId(crop_name, resource_name_id[i], group) != null) {
//        resource_id = resourceService.SearchResourceId(crop_name, resource_name_id[i], group);
//      }
//
//      System.out.println("resource_id = " + resource_id);
//
//      if (resource_id != 0) {
//        result = service.SelectTrait(resource_id);
//        break;
//      }
//    }
    
//    System.out.println(resource_id);
    
    result = service.SelectTrait(resource_id);
    
//    System.out.println("result : " + result);

    result.forEach(System.out::println);

    return result;
  }

  // 형질 분석
  @ResponseBody
  @RequestMapping("runAnalysis")
  public String RunAnalysis(
		  					@RequestParam("detail_name") String detail_name, 
		  					@RequestParam("detail_type") int detail_type, 
		  					@RequestParam("target_id[]") int[] target_id, 
		  					@RequestParam("target_name[]") String[] target_name, 
		  					@RequestParam("method") int method, 
		  					@RequestParam("trait_id") String trait_id,
		  					@RequestParam("resource_id") int resource_id) {
    
	  String result = null;

    List<Detail> detail = service.SelectDetail(detail_name, resource_id);
    List<StandardList> standard = service.SelectStandard(target_id, detail_type);

    
    System.out.println("target_id : " + Arrays.toString(target_id));
//    System.out.println("detail : " + detail);
//    System.out.println("standard : " + standard);
    
    
    Date date = new Date();

    String date_name = (1900 + date.getYear()) + "" + (date.getMonth() + 1) + "" + date.getDate() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();
    String file_name = date_name + "_phenotype_list.txt";

    String root = null;
    File path = null;
    File file = null;

    if (method == 0) {
      root = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/r_plot/corrplot/" + date_name;
//      root = "C:\\upload\\" + date_name;

      path = new File(root);
      file = new File(root + "/" + file_name);
      System.out.println("file make : " + path);
    } else {
      root = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/r_plot/trait/" + date_name;
//      root = "C:\\upload\\" + date_name;

      path = new File(root);
      file = new File(root + "/" + file_name);
    }

    try {
      if (!path.exists()) {
        path.mkdirs();
      }

      if (!file.exists()) {
        file.createNewFile();
      }

      BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

      
      for (int i = 0; i < detail.size(); i++) {
        writer.write("\t");
        writer.write("PH" + (i + 1));
      }
      
      writer.newLine();
      writer.write("aaaa");
      
      for(int i = 0; i < detail.size(); i++){
		writer.write("\t");
		System.out.print(detail.get(i).getDetail_name().replaceAll("[ |\r\n|\r|\n|\n\r]", "") + "\t");
		writer.write(detail.get(i).getDetail_name().replaceAll("[ |\r\n|\r|\n|\n\r]", ""));
      }
      
      for (int i = 0; i < standard.size(); i++) {
        if (i % detail.size() == 0) {
        	System.out.println("target_name[i / detail.size()] : " + target_name[i / detail.size()]);
          writer.newLine();
          writer.write(target_name[i / detail.size()]);
          writer.write("\t");
          if (standard.get(i).getStandard_data() == null) {
//            writer.write("");
        	  writer.write("NaN");
          } else {
            writer.write(standard.get(i).getStandard_data());
          }
          System.out.println("standard_" + i + " : " + standard.get(i).getStandard_data());
        } else {
          writer.write("\t");

          if (standard.get(i).getStandard_data() != (null)) {
            if (!standard.get(i).getStandard_data().equals("")) {
              writer.write(standard.get(i).getStandard_data());
            } else {
              writer.write("NaN");
            }
          } else {
            writer.write("NaN");
          }
        }
      }

      writer.newLine();
      writer.flush();
      writer.close();

      Path fileLocation = Paths.get(root + "/" + file_name).toAbsolutePath().normalize();
      Path targetLocation = fileLocation.resolve(file_name);

      String trait = trait_id.replace("[", "");
      trait = trait.replace("]", "");
      trait = trait.replace("\"", "");

      
      if (method == 0) {
        RunCorrlation runcorrlation = new RunCorrlation();
        runcorrlation.MakeCorrplot("c\\(" + trait + "\\)", "/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/", date_name);
//        runcorrlation.MakeCorrplot("c\\(" + trait + "\\)", "\"C:\\\\upload\\\\\"", date_name);
      } else {
        RunTrait runtrait = new RunTrait();
        runtrait.MakeTraitplot(trait, "/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/", date_name);
//        runtrait.MakeTraitplot(trait, "\"C:\\\\upload\\\\\"", date_name);
      } 
    } catch
    (IOException e) {
      System.out.println(e.getMessage());

      e.printStackTrace();
    }

    boolean check = false;

    if (method == 0) {
      File chk_file = new File("/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/r_plot/corrplot/" + date_name + "/" + date_name + "_corrplot.png");

      check = chk_file.exists();

      if (check) {
        result = "/common/resultfiles/r_plot/corrplot/" + date_name + "/" + date_name + "_corrplot.png";
      }
    } else {
      File chk_file = new File("/data/apache-tomcat-9.0.8/webapps/ROOT/common/resultfiles/r_plot/trait/" + date_name + "/" + date_name + "_traitplot.png");

      check = chk_file.exists();

      if (check) {
        result = "/common/resultfiles/r_plot/trait/" + date_name + "/" + date_name + "_traitplot.png";
      }
    }

    return result;
  }
}