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

import com.digitalresource.Entity.AnalysisDB;
import com.digitalresource.Service.AnalysisDbService;

@Controller
public class DigitalAnalysisController {
	
	@Autowired
	private AnalysisDbService service;
	
	@Autowired
	private FileController fileController;
	
	
	@RequestMapping("/digital_analysis")
	public ModelAndView digital_analysis(ModelAndView mv) {
		
		mv.setViewName("lab/digital_analysis");
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping("/search_analysis_db")
	public Map<String, Object> searchAnalysisDB() {
		
		Map<String, Object> result = new LinkedHashMap<>();
		
		List<AnalysisDB> analysis_db = service.searchAnalysisDB();
		
		result.put("analysis_db", analysis_db);
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insertAnalysisFile")
	public ModelAndView insertAnalysisFele(ModelAndView mv, 
											@ModelAttribute AnalysisDB analysis_db,
											@RequestParam("file") MultipartFile file) throws IOException {
		
		

		
		// 현재 날짜 구하기 및 포맷,문자열화
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		
		
		
		String[] extension = file.getOriginalFilename().split("\\.");

	    String file_name = fileController.ChangeFileName(extension[1]);
	    String origin_file_name = file.getOriginalFilename();
	    
//	    analysis_db.setMarker_file(file_name);
//	    analysis_db.setMarker_origin_file(origin_file_name);

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
 		System.out.println(analysis_db);
	    
	    // DB에 파일명 및 파일경로 지정
	    int insert_file = service.insertAnalysisDB(analysis_db);
		
		System.out.println(analysis_db);
		System.out.println("insert_file" + insert_file);
	    
	    mv.addObject("path", formatedNow);
		mv.setViewName("lab/matrix");
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping("delete_analysis_db")
	public int deleteAnalysisDB(@RequestParam("total_analysis_id[]") int[] total_analysis_id) {
		
		service.deleteAnalysisDB(total_analysis_id);
		
		return 1;
	}
	
	
	
}
