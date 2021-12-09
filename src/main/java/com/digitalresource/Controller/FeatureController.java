package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Service.CropService;
import com.digitalresource.Service.FeatureService;

@Controller
public class FeatureController {

	@Autowired
	private FeatureService featureService;

	@Autowired
	private FileController fileController;

	@Autowired
	private CropService cropService;

	@ResponseBody
	@RequestMapping("/seed-resources")
	public void seedResourcesInsert(
			@RequestParam(value = "attribute_item") String attribute_item,
			@RequestParam(value = "crop_name", required = false) String crop_name,
			@RequestParam("cropCategory") String cropCategory, String feature_count,
			@RequestParam(required = false) int cropSubCategory, MultipartFile file) throws IOException {

		System.out.println("=============seed-resources=================");
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

		int result = featureService.seedResourcesInsert(attribute_item, crop_name, cropCategory, file_name,
				origin_file_name, feature_count, cropSubCategory);
	}

	@PostMapping("updateSampleFile")
	public ModelAndView afterInsertTrait(ModelAndView mv) {
		 mv.setViewName("resource/Character_list");
	        return mv;
	}

}
