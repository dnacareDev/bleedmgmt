package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.StandardList;
import com.digitalresource.Mapper.AnalysisMapper;
import com.digitalresource.Service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService
{
	@Autowired
	private AnalysisMapper mapper;
	
	// 품종 조회
	@Override
	public List<Breed> SelectBreed(String name, int[] total_id, int type)
	{
		return mapper.SelectBreed(name, total_id, type);
	}

//	 분석 형질 조회
	@Override
	public List<Detail> selectTrait(String detail_name, int detail_type)
	{
		return mapper.selectTrait(detail_name, detail_type);
	}


	@Override
	public List<Detail> SelectTrait(int resource_id) {
		return mapper.SelectTrait(resource_id);
	}

	@Override
	public List<Detail> SelectDetail(String detail_name, int detail_type)
	{
		return mapper.SelectDetail(detail_name, detail_type);
	}

	@Override
	public List<StandardList> SelectStandard(int[] target_id, int detail_type)
	{
		return mapper.SelectStandard(target_id, detail_type);
	}
}