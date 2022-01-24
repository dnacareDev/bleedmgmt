package com.digitalresource.Service;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;

import java.util.List;

public interface AnalysisService
{
	// 파종 조회
	List<Breed> SelectBreed(String name, int[] total_id, int type);

	// 분석 형질 조회
	List<Detail> SelectTrait(String deatil_name, int detail_type);
	
	List<Detail> SelectDetail(String detail_name, int detail_type);

	List<StandardList> SelectStandard(int[] target_id, int detail_type);
}