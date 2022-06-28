package com.digitalresource.ServiceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.MarkerInformation;
import com.digitalresource.Mapper.MarkerInformationMapper;
import com.digitalresource.Service.MarkerInformationService;

@Service
public class MarkerInformationServiceImpl implements MarkerInformationService {

	@Autowired
	private MarkerInformationMapper mapper;
	

	@Override
	public List<MarkerInformation> searchMarkerInformation() {
		return mapper.searchMarkerInformation();
	}
	
	@Override
	public int InsertMarkerInformation(MarkerInformation marker_information) {
		return mapper.InsertMarkerInformation(marker_information);
	}
	
	@Override
	public Integer DeleteMarkerInformation(int[] total_marker_num) {
		return mapper.DeleteMarkerInformation(total_marker_num);
	}
}
