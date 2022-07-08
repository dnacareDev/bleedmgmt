package com.digitalresource.Service;

import java.util.List;

import com.digitalresource.Entity.MabcDesign;

public interface MabcDesignService {

	public List<MabcDesign> searchMabcSample();
	
	public int insertMabcDesign(MabcDesign mabc_design);
	
	public Integer DeleteMabcDesign(int[] total_mabc_num);

}
