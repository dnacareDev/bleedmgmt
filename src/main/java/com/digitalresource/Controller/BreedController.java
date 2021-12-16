package com.digitalresource.Controller;

import com.digitalresource.Entity.BreedFile;
import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private FileController fileController;

	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv, @RequestParam(value="type") String type,@RequestParam(value="id") int resource_id,@RequestParam(value="crop_id", required = false)int crop_id) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		List<Crop> crops = cropService.SearchCropList(type);
	
		mv.addObject("crop_id",crop_id);
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

		for(int i = 0; i < resourceNameId.length; i++) {
			if(resourceService.SearchResourceId(crop_id, resourceNameId[i]) != null) {
				resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i]);
				break;
			}
		}
		List<Detail> details = detailService.SelectDetailListByResource(resourceId);	
		List<Map<String, Object>> standardList = breedService.selectStandard(resourceId);
		result.put("standardList",standardList);
		result.put("detail", details);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("updateStandardCell")
	public int updateStandardCell(@RequestParam(value="standard_data") String standard_data,@RequestParam(value="standard_id") int standard_id) {
		int result = 0;
		
		StandardList standard = new StandardList();
		standard.setStandard_data(standard_data);
		standard.setStandard_id(standard_id);
		result = breedService.updateStandardCell(standard);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("insertBreed2")
	public int insertBreed(@RequestParam(value="data")String data, @RequestParam(value="resource_id") int resource_id, @RequestParam(value="crop_id")int crop_id, @RequestParam(value="resource_name") String resource_name) {
		int result = 0;
		
		result = breedService.insertBreed(resource_id,data,crop_id,resource_name);

		return result;
	}

	@ResponseBody
	@RequestMapping("selectBreedDateGroup")
	public Map<String, Object> SelectDateGroup(@RequestParam("datalist_type") String datalist_type) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();

//		List<Map<String, String>> dataGroup = d_service.SelectDateGroup(datalist_type);
//
//		result.put("dataGroup", dataGroup);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("deleteBreed")
	public int deleteBreed(String breed_id, String breed_row) {
		int result = breedService.deleteBreed(breed_id,breed_row);
		return result;
	}

	// 첨부파일 등록
	@RequestMapping("insertBreedFile")
	public ModelAndView InsertBreedFile(ModelAndView mv, @ModelAttribute BreedFile breed_file, @RequestParam("file") MultipartFile file) throws IOException {
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

		int insert_file = service.InsertBreedFile(breed_file);

		Uploads upload = new Uploads();
		upload.setUploads_file(file_name);
		upload.setUploads_origin_file(origin_file_name);
		upload.setBreed_file_id(breed_file.getBreed_file_id());

		int insert_upload = service.InsertBreedUpload(upload);

		mv.setViewName("redirect:/breed");

		return mv;
	}
}
