package com.digitalresource.ServiceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.MabcDesign;
import com.digitalresource.Mapper.MabcDesignMapper;
import com.digitalresource.Service.MabcDesignService;

@Service
public class MabcDesignServiceImpl implements MabcDesignService {

	@Autowired
	private MabcDesignMapper mapper;
	
	
	@Override
	public List<MabcDesign> searchMabcSample() {
		return mapper.searchMabcSample();
	}
	
	@Override
	public int insertMabcDesign(MabcDesign mabc_design) {
		System.out.println(mabc_design);
		return mapper.insertMabcDesign(mabc_design);
	}
	
}
