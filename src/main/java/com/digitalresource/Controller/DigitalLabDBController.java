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

import com.digitalresource.Entity.MarkerInformation;
import com.digitalresource.Service.MarkerInformationService;

@Controller
public class DigitalLabDBController {
	
	@Autowired
	private MarkerInformationService MService;
	
	@Autowired
	private FileController fileController;
	
	
	@RequestMapping("/digital_lab_DB")
	public ModelAndView digitalLabDB(ModelAndView mv) {
		
		mv.setViewName("lab/digital_lab_DB");
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping("/search_marker_information")
	public Map<String, Object> SearchMarkerInfomation() {

		Map<String, Object> result = new LinkedHashMap<String, Object>();

		List<MarkerInformation> marker_information = MService.searchMarkerInformation();
		
	    result.put("marker_information", marker_information);
		
	    return result;
	}
	
	@ResponseBody
	@RequestMapping("/insertMarkerFile")
	public ModelAndView InsertMarkerFile(ModelAndView mv,											
										@ModelAttribute MarkerInformation marker_information,
									   	@RequestParam("file") MultipartFile file) throws IOException {
								

		// 현재 날짜 구하기 및 포맷,문자열화
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		
		
		String[] extension = file.getOriginalFilename().split("\\.");

	    String file_name = fileController.ChangeFileName(extension[1]);
	    String origin_file_name = file.getOriginalFilename();
	    
	    marker_information.setMarker_file(file_name);
	    marker_information.setMarker_origin_file(origin_file_name);

//				    String path = "src/main/webapp/upload";
	    String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

	    File filePath = new File(path);

	    if (!filePath.exists())
	      filePath.mkdirs();
	    
	    Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
	    Path targetLocation = fileLocation.resolve(file_name);
	    
	    // 서버에 첨부파일 업로드
	    Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	    
	    // html에서 제출된 name들의 정상출력 여부 + file_name 세팅
 		System.out.println(marker_information);
	    
	    // DB에 파일명 및 파일경로 지정
	    int insert_file = MService.InsertMarkerInformation(marker_information);
	    
		
		mv.setViewName("redirect:/digital_lab_DB");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("delete_marker_information")
	public int DeleteMarkerInformation(@RequestParam("total_marker_num[]") int[] total_marker_num) {
		// System.out.println(); 
		
		MService.DeleteMarkerInformation(total_marker_num);
		
		return 1;
	}
}

