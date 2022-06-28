package com.digitalresource.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.DigitalTools.RunMarkerDB;
import com.digitalresource.Entity.ChromosomeViewer;
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
	
	// QTL 맵 분석
	@ResponseBody
	@RequestMapping("MarkerMap") 
	public String MarkerMap(@RequestParam("total_file_name[]") String[] total_file_name) throws IOException {		
														
		
		// 현재 날짜 구하기 및 포맷,문자열화
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		String path = null;
		String newPath = null;
		
		
		for(int i=0 ; i<total_file_name.length ; i++) {
			System.out.println(total_file_name[i]);
			
			
			path = "/data/apache-tomcat-9.0.8/webapps/ROOT/upload/" + total_file_name[i];
			newPath = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/markerDB/" + formatedNow + "/";
			
			File file = new File(path);
			File newFile = new File(newPath + file.getName());
			
			if (!newFile.exists())
				newFile.mkdirs();
			
			try 
	        {
	        Files.copy(file.toPath(), newFile.toPath(),StandardCopyOption.REPLACE_EXISTING);  
	          // Static Methods To Copy Copy source path to destination path
	        } catch(Exception e) {
	             System.out.println(e);  // printing in case of error.
	        }
			// 여기까지 체크한 엑셀파일들을 서버에 업로드
			
			 //파일 병합
		    RunMarkerDB runMarkerDB = new RunMarkerDB();			// 받은 java파일로 한거니까 marker로 수정해야함
			runMarkerDB.MakeRunMarkerDB(formatedNow);
			StringTokenizer st = null;
			String str1 = null;
			String str2 = null;
			String str3 = null;
			
			ChromosomeViewer chromosomeViewer = null;
			
			
			System.out.println("check path : " + newPath + formatedNow + ".csv");
			//csv 읽기
			File csvFile = new File(newPath + formatedNow + ".csv");
			if(csvFile.exists()) { 
				BufferedReader inFile = new BufferedReader(new FileReader(csvFile)); 
				inFile.readLine();	// 한줄을 미리 읽어서 버린다
				String sLine = null; 
				while( (sLine = inFile.readLine()) != null ) {
//							System.out.println("sLine"); 					//읽어들인 문자열을 출력 합니다. 
					st = new StringTokenizer(sLine, ",");
					str1 = st.nextToken().replaceAll("\"", "");
					str2 = st.nextToken().replaceAll("\"", "");
					str3 = st.nextToken().replaceAll("\"", "");
					System.out.print(str1+ " ");
					System.out.print(str2+ " ");
					System.out.println(str3);
					
					chromosomeViewer = new ChromosomeViewer();
					
					chromosomeViewer.setStr1(str1);
					chromosomeViewer.setStr2(str2);
					chromosomeViewer.setStr3(str3);
					chromosomeViewer.setSel(0);
					chromosomeViewer.setJobid(formatedNow);
					
//					System.out.println(chromosomeViewer);
					
//					int result = service.InsertChromosomeViewer(chromosomeViewer);
				}
			}
		}
		
		return formatedNow;
		
	}
}

