package com.digitalresource.ServiceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.MabcSample;
import com.digitalresource.Mapper.MabcSampleMapper;
import com.digitalresource.Service.MabcSampleService;

@Service
public class MabcSampleServiceImpl implements MabcSampleService {

	@Autowired
	private MabcSampleMapper mapper;
	
	
	
	@Override
	public List<MabcSample> searchMabcSample() {
		return mapper.searchMabcSample();
	}
	
	@Override
	public int insertMabcSample(MabcSample mabc_sample) {
		return mapper.insertMabcSample(mabc_sample);
	}
	
	@Override
	public Integer deleteMabcSample(int[] total_mabc_id) {
		return mapper.deleteMabcSample(total_mabc_id);
	}
	
}
