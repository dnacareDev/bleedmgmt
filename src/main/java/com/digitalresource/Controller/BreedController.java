package com.digitalresource.Controller;

import com.digitalresource.Entity.*;
import com.digitalresource.Service.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import javax.servlet.http.HttpServlet;

import org.json.JSONArray;
import org.json.JSONObject;
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
	private DataListService datalistService;

	@Autowired
	private FileController fileController;

	@RequestMapping("/breed")
	public ModelAndView breed(ModelAndView mv, @RequestParam(value="type") String type, @RequestParam(value="id") int resource_id, @RequestParam(value="crop_id", required = false) int crop_id) {
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

		for(int i = 0; i < resourceNameId.length; i++) {
			if(resourceService.SearchResourceId(crop_id, resourceNameId[i]) != null) {
				resourceId = resourceService.SearchResourceId(crop_id, resourceNameId[i]);
				break;
			}
		}

		List<Detail> details = detailService.SelectDetailListByResource(resourceId);	
		List<Map<String, Object>> standardList = breedService.selectStandard(resourceId);

		String crop_name = breedService.SearchCropName(crop_id);

		List<Breed> breed = breedService.SearchBreed(crop_name);


		result.put("breed", breed);
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

		System.out.println(data);

		result = breedService.insertBreed(resource_id,data,crop_id,resource_name);

		return result;
	}
	
	@ResponseBody
	@RequestMapping("deleteBreed")
	public int deleteBreed(String breed_id, String breed_row) {
		System.out.println(breed_id);
		System.out.println("breed_id ========================");
		System.out.println(breed_row);
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

		int insert_file = breedService.InsertBreedFile(breed_file);

		Uploads upload = new Uploads();
		upload.setUploads_file(file_name);
		upload.setUploads_origin_file(origin_file_name);
		upload.setBreed_file_id(breed_file.getBreed_file_id());

		int insert_upload = breedService.InsertBreedUpload(upload);

		mv.setViewName("redirect:/breed");

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
				standard.setStandard_data((String) item.ㅠget(j + 1));

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
		List<StandardList> result = breedService.SelectBreedStandard(breed_id);

		return result;
	}
}
