package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.User;
import com.digitalresource.RModule.RunEtcR;

@Controller
public class LabController
{
	@RequestMapping("matrix")
	public ModelAndView Matrix(ModelAndView mv)
	{
		mv.setViewName("lab/matrix");
		
		return mv;
	}
	
	@RequestMapping("/insertMatrix")
	public ModelAndView InsertMatrix(ModelAndView mv, Authentication auth, @RequestParam("file") MultipartFile file) throws IOException
	{
		User user = (User)auth.getPrincipal();
		
		Date date = new Date();
		
        String date_name = (1900 + date.getYear()) + "" + (date.getMonth() + 1) + "" + date.getDate() + "" + date.getHours() + "" + date.getMinutes() + "" + date.getSeconds();
        String origin_name = file.getOriginalFilename();
        String file_name = date_name + "_" + origin_name;
        
        String path = "/data/apache-tomcat-9.0.8/webapps/ROOT/common/r/result/" + date_name;
        
        File filePath = new File(path);
        
        if (!filePath.exists())
        	filePath.mkdirs();
        
       	Path fileLocation = Paths.get(path).toAbsolutePath().normalize();
       	Path targetLocation = fileLocation.resolve(file_name);
		
       	Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
       	
       	/*
       	AnalysisFile analysis = new AnalysisFile();
       	analysis.setUser_id(user.getUser_id());
       	analysis.setAnalysis_type(1);
       	analysis.setAnalysis_file(file_name);
       	analysis.setAnalysis_origin_file(origin_name);
       	
       	int result = service.InsertAnalysisFile(analysis);
       	 */
       	
       	RunEtcR runetcr = new RunEtcR();
       	runetcr.MakeRunEtcR(date_name, file_name);
		
		mv.setViewName("redirect:/matrix");
		
		return mv;
	}
}