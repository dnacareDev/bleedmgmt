package com.digitalresource.ServiceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.AnalysisDB;
import com.digitalresource.Mapper.AnalysisDbMapper;
import com.digitalresource.Service.AnalysisDbService;

@Service
public class AnalysisDbServiceimpl implements AnalysisDbService {

	@Autowired
	AnalysisDbMapper mapper;
	
	
	@Override
	public List<AnalysisDB> searchAnalysisDB() {
		return mapper.searchAnalysisDB();
	}
	
	@Override
	public int insertAnalysisDB(AnalysisDB analysis_db) {
		System.out.println(analysis_db);
		return mapper.insertAnalysisDB(analysis_db);
	}	
	
	@Override
	public Integer deleteAnalysisDB(int[] total_analysis_id) {
		return mapper.deleteAnalysisDB(total_analysis_id);
	}
}
