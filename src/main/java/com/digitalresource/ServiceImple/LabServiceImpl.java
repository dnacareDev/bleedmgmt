package com.digitalresource.ServiceImple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.AnalysisFile;
import com.digitalresource.Mapper.LabMapper;
import com.digitalresource.Service.LabService;

@Service
public class LabServiceImpl implements LabService
{
	@Autowired
	private LabMapper mapper;
	
	@Override
	public AnalysisFile SelectAnalysisFile(int user_id, int analysis_type)
	{
		return mapper.SelectAnalysisFile(user_id, analysis_type);
	}
	
	@Override
	public int InsertAnalysisFile(AnalysisFile analysis)
	{
		return mapper.InsertAnalysisFile(analysis);
	}
}