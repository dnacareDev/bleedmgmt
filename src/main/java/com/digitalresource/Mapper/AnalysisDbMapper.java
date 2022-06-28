package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalresource.Entity.AnalysisDB;

@Mapper
public interface AnalysisDbMapper {
	
	
	public List<AnalysisDB> searchAnalysisDB();
	
	public int insertAnalysisDB(AnalysisDB analysis_db);
	
	public Integer deleteAnalysisDB(int[] total_analysis_id);
}
