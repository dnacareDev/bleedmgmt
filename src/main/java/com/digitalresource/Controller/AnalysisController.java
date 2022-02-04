package com.digitalresource.Controller;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.RModule.RunCorrlation;
import com.digitalresource.RModule.RunTrait;
import com.digitalresource.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
  public ModelAndView Analysis(ModelAndView mv) {

    String type = "파종대장";

    List<Crop> crops = cropService.SearchCropList(type);

    mv.addObject("cropList", crops);

    mv.setViewName("lab/analysis");

    return mv;
  }

  // 작목 조회
  @ResponseBody
  @RequestMapping("/selectTarget")
  public Map<String, Object> SelectTarget(@RequestParam("name") String name, @RequestParam(required = false, value = "total_id") int[] total_id, @RequestParam("type") int type) {
    Map<String, Object> result = new LinkedHashMap<String, Object>();

    List<Breed> breed = new ArrayList<Breed>();

    int crop_id = service.SelectCropIdByName(name);

    String resourceName = "파종대장";

    int[] resource_name_id = resourceNameService.SelectResourceNameId(resourceName);

    int[] resource_id = new int[resource_name_id.length];

    for (int i = 0; i < resource_name_id.length; i++) {
      if (resourceService.SearchResourceId(crop_id, resource_name_id[i]) != null) {
        resource_id[i] = resourceService.SearchResourceId(crop_id, resource_name_id[i]);
      }
    }

    breed = service.SelectBreed(name, resource_id, type);

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
  public List<Detail> SelectTrait1(@RequestParam("crop_id") int crop_id) {
    List<Detail> result = new ArrayList<>();

    String resourceName = "파종대장";

    int[] resource_name_id = resourceNameService.SelectResourceNameId(resourceName);

    for (int i = 0; i < resource_name_id.length; i++) {
      int resource_id = 0;

      if (resourceService.SearchResourceId(crop_id, resource_name_id[i]) != null) {
        resource_id = resourceService.SearchResourceId(crop_id, resource_name_id[i]);
      }

      if (resource_id != 0) {
        result = service.SelectTrait(resource_id);
        break;
      }
    }

    return result;
  }

  // 형질 분석
  @ResponseBody
  @RequestMapping("runAnalysis")
  public String RunAnalysis(@RequestParam("detail_name") String detail_name, @RequestParam("detail_type") int detail_type, @RequestParam("target_id[]") int[] target_id, @RequestParam("target_name[]") String[] target_name, @RequestParam("method") int method, @RequestParam("trait_id") String trait_id) {
    String result = null;

    List<Detail> detail = service.SelectDetail(detail_name);
    List<StandardList> standard = service.SelectStandard(target_id, detail_type);

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

      for (int i = 0; i < standard.size(); i++) {
        if (i % detail.size() == 0) {
          writer.newLine();
          writer.write(target_name[i / detail.size()]);
          writer.write("\t");
          if (standard.get(i).getStandard_data() == null) {
            writer.write("");
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