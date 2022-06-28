package com.digitalresource.Service;

import java.util.List;

import com.digitalresource.Entity.MabcSample;

public interface MabcSampleService {
	
	public List<MabcSample> searchMabcSample();
	
	public int insertMabcSample(MabcSample mabc_sample);
	
	public Integer deleteMabcSample(int[] total_mabc_id);
	
}
