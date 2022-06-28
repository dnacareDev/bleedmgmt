package com.digitalresource.Service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.digitalresource.Entity.AnalysisDB;

public interface AnalysisDbService {
	
	public List<AnalysisDB> searchAnalysisDB();
	
	public int insertAnalysisDB(AnalysisDB analysis_db);
	
	public Integer deleteAnalysisDB(int[] total_analysis_id);
	
}
