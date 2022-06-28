package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.MabcSample;
import com.digitalresource.Service.MabcSampleService;



@Controller
public class DigitalMabcDBController {
	
	@Autowired
	private MabcSampleService Service;
	
	@Autowired
	private FileController fileController;

	@RequestMapping("/digital_MABC_DB")
	public ModelAndView digitalMabcDB(ModelAndView mv) {
		
		mv.setViewName("lab/digital_MABC_DB");
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping("/search_mabc_sample")
	public Map<String, Object> searchMabcSample() {
		
		Map<String,Object> result = new LinkedHashMap<>();
		
		List<MabcSample> mabc_sample = Service.searchMabcSample();
		
		result.put("mabc_sample", mabc_sample);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insertMabcFile")
	public ModelAndView InsertMarkerFile(ModelAndView mv,											
										@ModelAttribute MabcSample mabc_sample,
									   	@RequestParam("file") MultipartFile file) throws IOException {
	

		// 현재 날짜 구하기 및 포맷,문자열화
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		
		
		String[] extension = file.getOriginalFilename().split("\\.");
		
		String file_name = fileController.ChangeFileName(extension[1]);
		String origin_file_name = file.getOriginalFilename();
		
		mabc_sample.setFile_name(file_name);
		mabc_sample.setFile_origin_name(origin_file_name);
		
		//String path = "src/main/webapp/upload";
		String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";
		
		File filePath = new File(path);
		
		if (!filePath.exists())
		filePath.mkdirs();
		
		Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
		Path targetLocation = fileLocation.resolve(file_name);
		
		// 서버에 첨부파일 업로드
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		
		// html에서 제출된 name들의 정상출력 여부 + file_name 세팅
		System.out.println(mabc_sample);
		
		// DB에 파일명 및 파일경로 지정
		int insert_file = Service.insertMabcSample(mabc_sample);
		
		mv.setViewName("redirect:/digital_MABC_DB");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("delete_mabc_sample")
	public int DeleteMabcSample(@RequestParam("total_mabc_id[]") int[] total_mabc_id) {
		// System.out.println(); 
		
		Service.deleteMabcSample(total_mabc_id);
		
		return 1;
	}
	
}
