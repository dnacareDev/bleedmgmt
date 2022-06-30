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

import com.digitalresource.Entity.MabcDesign;
import com.digitalresource.Service.MabcDesignService;


@Controller
public class DigitalMabcDesignController {
	
	
	@Autowired
	private MabcDesignService service;
	
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
	public String insertMabcDesign( @ModelAttribute MabcDesign mabc_design,
//														@RequestParam("htwo") String htwo,
//														@RequestParam("nbchromosome") String nbchromosome,
//														@RequestParam("numberofrepeats") String numberofrepeats,
//														@RequestParam("gtrainingpop") String gtrainingpop,
//														@RequestParam("geneticlengthf") String geneticlengthf,
//														@RequestParam("geneticlengthm") String geneticlengthm,
//														@RequestParam("nbqtls") String nbqtls,
//														@RequestParam("nbsnps") String nbsnps,
//														@RequestParam("nbkeepqtl") String nbkeepqtl,
//														@RequestParam("nugammaf") String nugammaf,
//														@RequestParam("pf") String pf,
//														@RequestParam("nugammam") String nugammam,
//														@RequestParam("pm") String pm,
//														@RequestParam("crop") String crop,
//														@RequestParam("note") String note,
														
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

		// html에서 제출된 name들이 정상출력됐는지 여부
		
//		String[] extension = file1.getOriginalFilename().split("\\.");
//
//	    String file_name = fileController.ChangeFileName(extension[1]);
//	    String origin_file_name = file1.getOriginalFilename();

//	    String path = "src/main/webapp/upload";
	    
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
	    
//	    Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
//	    Path targetLocation = fileLocation.resolve(file_name);
	    
	    // 서버에 첨부파일 업로드
	    
	    Files.copy(file1.getInputStream(), target1, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file2.getInputStream(), target2, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file3.getInputStream(), target3, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file4.getInputStream(), target4, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file5.getInputStream(), target5, StandardCopyOption.REPLACE_EXISTING);
	    Files.copy(file6.getInputStream(), target6, StandardCopyOption.REPLACE_EXISTING);
	    // DB에 파일명 및 파일경로 지정
	    
	    String jobid = formatedNow;
//	    mabc_design.setCrop(crop);
	    mabc_design.setJobid(jobid);
	    service.insertMabcDesign(mabc_design);
	    
//	    mv.setViewName("lab/MABC_parameter");
//	    mv.addObject("NbChromosome", NbChromosome);
//	    mv.addObject("NumberOfRepeats", NumberOfRepeats);
//	    mv.addObject("gTrainingPop", gTrainingPop);
//	    mv.addObject("geneticLengthF", geneticLengthF);
//	    mv.addObject("geneticLengthM", geneticLengthM);
//	    mv.addObject("NbQTLs", NbQTLs);
//	    mv.addObject("NbSNPs", NbSNPs);
//	    mv.addObject("NbKeepQTL", NbKeepQTL);
//	    mv.addObject("nuGammaF", nuGammaF);
//	    mv.addObject("nuGammaM", nuGammaM);
//	    mv.addObject("pM", pM);
//	    mv.addObject("jobid", formatedNow);
	    
//	    mv.setViewName("lab/MABCDesign");

	    System.out.println(formatedNow);
	    
	    return formatedNow;
	    
	}
	
	
}
