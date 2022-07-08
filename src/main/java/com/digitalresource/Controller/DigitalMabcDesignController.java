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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.MabcDesign;
import com.digitalresource.Entity.User;
import com.digitalresource.Service.LogService;
import com.digitalresource.Service.MabcDesignService;


@Controller
public class DigitalMabcDesignController {
	
	
	@Autowired
	private MabcDesignService service;

	@Autowired
	private LogService logService;
	
	@Autowired
	private FileController fileController;
	


	@RequestMapping("/digital_MABC_Design")
	public ModelAndView digitalMabcDesign(ModelAndView mv) {
		
		mv.setViewName("lab/digital_MABC_Design");
		
		return mv;
		
	}
	
	@ResponseBody
	@RequestMapping("/search_mabc_design")
	public Map<String, Object> searchMabcSample() {
		
		Map<String,Object> result = new LinkedHashMap<>();
		
		List<MabcDesign> mabc_sample = service.searchMabcSample();
		
		result.put("mabc_sample", mabc_sample);
		
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping("insertMABCDesign") 
	public String insertMabcDesign( Authentication auth, @ModelAttribute MabcDesign mabc_design,
														@RequestParam("file1") MultipartFile file1,
														@RequestParam("file2") MultipartFile file2,
														@RequestParam("file3") MultipartFile file3,
														@RequestParam("file4") MultipartFile file4,
														@RequestParam("file5") MultipartFile file5,
														@RequestParam("file6") MultipartFile file6 ) throws IOException {  
																		
														// 아직 html쪽에 file 변수 없음
		
		 
		// 현재 날짜 구하기 및 포맷,문자열화
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

	    // 기존 업로드 폴더에 업로드날짜를 이름으로하는 새로운 폴더(경로)를 추가
	    String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/";
	    //String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/";

	    Path target1 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/positionQTLsF.csv");
	    Path target2 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/positionQTLsM.csv");
	    Path target3 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/positionSNPsF.csv");
	    Path target4 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/positionSNPsM.csv");
	    Path target5 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/weightMrkrs_Cold.csv");
	    Path target6 = Paths.get("/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/MABC/" + formatedNow + "/GenerationParameters.csv");
	    
	    File filePath = new File(path);

	    if (!filePath.exists())
	      filePath.mkdirs();
	    
	    String time = formatedNow;
	    
	    // 서버에 첨부파일 업로드
	    
	    Files.copy(file1.getInputStream(), target1, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file2.getInputStream(), target2, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file3.getInputStream(), target3, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file4.getInputStream(), target4, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file5.getInputStream(), target5, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file6.getInputStream(), target6, StandardCopyOption.REPLACE_EXISTING);
	    // DB에 파일명 및 파일경로 지정
	    
	    String jobid = formatedNow;
	    mabc_design.setJobid(jobid);
	    service.insertMabcDesign(mabc_design);
	    
	    System.out.println(formatedNow);
	    
	    User user = (User) auth.getPrincipal();
	    String userIdName = user.getUser_username(); 
		String userName = user.getUser_name();
		String log_contents = "MABC Simulation 1행 입력";
		logService.RecordLog(userIdName, userName, log_contents);
	    
	    return formatedNow;
	    
	}
	
	
	@ResponseBody
	@RequestMapping("delete_mabc_design")
	public int DeleteMarkerInformation(Authentication auth, @RequestParam("total_mabc_num[]") int[] total_mabc_num) {
		
		service.DeleteMabcDesign(total_mabc_num);
		
		User user = (User) auth.getPrincipal();
	    String userIdName = user.getUser_username(); 
		String userName = user.getUser_name();
		String log_contents = "MABC 시뮬레이션 " + total_mabc_num.length + "행 삭제";
		logService.RecordLog(userIdName, userName, log_contents);
		
		return 1;
	}
	
	
}
