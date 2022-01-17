package com.digitalresource.Service;

import com.digitalresource.Entity.AnalysisFile;

public interface LabService
{
	AnalysisFile SelectAnalysisFile(int user_id, int analysis_type);
	
	int InsertAnalysisFile(AnalysisFile analysis);
}