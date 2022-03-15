package com.digitalresource.Service;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnalysisService
{
	// 파종 조회
	List<Breed> SelectBreed(String name, int[] resource_id, int type);

	List<Breed> SelectBreed2(String name, int resource_id, int type);

	// 분석 형질 조회
	List<Detail> selectTrait(String detail_name, int detail_type);

	List<Detail> SelectTrait(int resource_id);

	List<Detail> SelectDetail(String detail_name);

	List<StandardList> SelectStandard(int[] target_id, int detail_type);

	int SelectCropIdByName(String crop_name);
}