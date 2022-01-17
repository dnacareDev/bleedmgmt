package com.digitalresource.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalresource.Entity.AnalysisFile;

@Mapper
public interface LabMapper
{
	AnalysisFile SelectAnalysisFile(@Param("user_id") int user_id, @Param("analysis_type") int analysis_type);
	
	int InsertAnalysisFile(AnalysisFile analysis);
}