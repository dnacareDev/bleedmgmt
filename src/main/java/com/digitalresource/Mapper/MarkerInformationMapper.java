package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalresource.Entity.MarkerInformation;

@Mapper
public interface MarkerInformationMapper {

	
	public List<MarkerInformation> searchMarkerInformation();
	
	public int InsertMarkerInformation(MarkerInformation marker_information);
	
	public Integer DeleteMarkerInformation(int[] total_marker_num);
	
}
