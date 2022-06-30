package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalresource.Entity.MabcDesign;

@Mapper
public interface MabcDesignMapper {

	
	public List<MabcDesign> searchMabcSample();
	
	public int insertMabcDesign(MabcDesign mabc_design);
}
