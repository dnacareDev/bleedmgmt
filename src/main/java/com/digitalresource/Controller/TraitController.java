package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.CropCategory;
import com.digitalresource.Entity.Trait;
import com.digitalresource.Service.CropCategoryService;
import com.digitalresource.Service.TraitService;
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
public class TraitController {

    @Autowired
    private CropCategoryService cropCategoryService;

    @Autowired
    private TraitService traitService;

    @RequestMapping("character")
    public ModelAndView characterList(ModelAndView mv){
        mv.setViewName("resource/Character_list");
        return mv;
    }

    @PostMapping("searchTrait")
    @ResponseBody
    public Map<String, Object> searchTrait(@RequestParam("page_num") int page_num, @RequestParam("limit") int limit){
        Map<String,Object> map = new HashMap<>();

        int count = traitService.SelectTraitCount();

        int offset = (page_num - 1) * limit;
        int end_page = (count + limit - 1) / limit;

        List<Trait> traits = traitService.SelectTraitList();

        map.put("trait", traits);
        map.put("page_num", page_num);
        map.put("end_page", end_page);
        map.put("offset", offset);

        return map;
    }

    @RequestMapping("regist-character")
    public ModelAndView registTrait(ModelAndView mv){
        List<CropCategory> categoryList = cropCategoryService.SelectCropCategoryList();

        mv.addObject("cropCategoryList", categoryList);

        mv.setViewName("resource/regist_character");

        return mv;
    }

    @ResponseBody
    @RequestMapping("cropList")
    public List<Crop> selectCropByCategoryId(@RequestParam("category_id") int category_id) {
        List<Crop> cropList = cropCategoryService.SelectCropByCategory(category_id);

        return cropList;
    }
    
    @ResponseBody
    @RequestMapping("change-trait")
    public int changeTrait(@RequestParam("trait_description") String trait_description, @RequestParam("limit") int limit) {
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("trait_description", trait_description);
    	param.put("limit", limit);
    	int result = traitService.changeTrait(param);
    	return result;
    }

    @ResponseBody
    @RequestMapping("traitDescriptionList")
    public List<Trait> traitDescriptionList(){
    	List<Trait> traitList = traitService.traitDescriptionList();
    	return traitList;
    }

    @ResponseBody
    @RequestMapping("change-year")
    public int changeYear(@RequestParam("trait_year") int trait_year, @RequestParam("limit") int limit) {
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("trait_year", trait_year);
        param.put("limit", limit);
        int result = traitService.changeYear(param);
        return result;
    }
}
