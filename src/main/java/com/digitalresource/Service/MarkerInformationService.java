package com.digitalresource.Service;

import java.util.List;

import com.digitalresource.Entity.ChromosomeViewer;
import com.digitalresource.Entity.MarkerInformation;


public interface MarkerInformationService {
	
	public List<MarkerInformation> searchMarkerInformation();
	
	public int InsertMarkerInformation(MarkerInformation marker_information);
	
	public Integer DeleteMarkerInformation(int[] total_marker_num);
	
	public int InsertChromosomeViewer(ChromosomeViewer chromosomeViewer);
}
