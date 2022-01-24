package com.digitalresource.Controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.digitalresource.Entity.AnalysisFile;
import com.digitalresource.Entity.User;
import com.digitalresource.RModule.RunEtcR;
import com.digitalresource.Service.LabService;

@Controller
public class LabController
{
	@Autowired
	private LabService service;

	@RequestMapping("/correlation")
	public ModelAndView Correlation(ModelAndView mv, @RequestParam(required = false, value = "crop") String crop, @RequestParam(required = false, value = "total_id") int[] total_id, @RequestParam(defaultValue = "0", value = "type") int type)
	{
		mv.addObject("crop", crop);
		mv.addObject("total_id", total_id);
		mv.addObject("type", type);

		mv.setViewName("lab/correlation");

		return mv;
	}

	@RequestMapping("/traitView")
	public ModelAndView TraitView(ModelAndView mv, @RequestParam(required = false, value = "crop") String crop, @RequestParam(required = false, value = "total_id") int[] total_id, @RequestParam(defaultValue = "0", value = "type") int type)
	{
		mv.addObject("crop", crop);
		mv.addObject("total_id", total_id);
		mv.addObject("type", type);

		mv.setViewName("lab/trait_view");

		return mv;
	}

	@RequestMapping("/mabc")
	public ModelAndView getDataManage(ModelAndView mv, Authentication auth)
	{
		User user = (User)auth.getPrincipal();

		int analysis_type = 0;

		AnalysisFile analysis = service.SelectAnalysisFile(user.getUser_id(), analysis_type);

		if(analysis != null)
		{
			analysis.setAnalysis_file("/common/r/result/" + analysis.getAnalysis_file());
		}

		mv.addObject("analysis", analysis);

		mv.setViewName("lab/mabc");

		return mv;
	}

	@RequestMapping("/matrix")
	public ModelAndView Analysis(ModelAndView mv, Authentication auth)
	{
		User user = (User)auth.getPrincipal();
		
		int analysis_type = 1;
		
		AnalysisFile analysis = service.SelectAnalysisFile(user.getUser_id(), analysis_type);
		
		if(analysis != null)
		{
			String[] extension = analysis.getAnalysis_file().split("_");
			
			mv.addObject("analysis", analysis);
			mv.addObject("path", extension[0]);
		}
		
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
       	
       	AnalysisFile analysis = new AnalysisFile();
       	analysis.setUser_id(user.getUser_id());
       	analysis.setAnalysis_type(1);
       	analysis.setAnalysis_file(file_name);
       	analysis.setAnalysis_origin_file(origin_name);
       	
       	int result = service.InsertAnalysisFile(analysis);
       	
       	RunEtcR runetcr = new RunEtcR();
       	runetcr.MakeRunEtcR(date_name, file_name);
		
		mv.setViewName("redirect:/matrix");
		
		return mv;
	}
}