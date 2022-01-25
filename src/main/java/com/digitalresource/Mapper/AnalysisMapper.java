package com.digitalresource.Mapper;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalysisMapper
{
	// 품종 조회
	List<Breed> SelectBreed(@Param("name") String name, @Param("total_id") int[] total_id, @Param("type") int type);

	// 분석 형질 조회
	List<Detail> selectTrait(@Param("detail_name") String detail_name, @Param("detail_type") int detail_type);

	List<Detail> SelectTrait(@Param("resource_id") int resource_id);

	List<Detail> SelectDetail(@Param("detail_name") String detail_name, @Param("detail_type") int detail_type);

	List<StandardList> SelectStandard(@Param("target_id") int[] target_id, @Param("detail_type") int detail_type);
}