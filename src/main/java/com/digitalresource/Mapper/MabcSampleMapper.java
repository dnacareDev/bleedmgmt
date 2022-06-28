package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalresource.Entity.MabcSample;

@Mapper
public interface MabcSampleMapper {
	
	public List<MabcSample> searchMabcSample();
	
	public int insertMabcSample(MabcSample mabc_sample);
	
	public Integer deleteMabcSample(int[] total_mabc_id);
	
}
