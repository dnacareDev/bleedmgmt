package com.digitalresource.Service;

import java.util.List;

import com.digitalresource.Entity.StandardList;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data);
	
	List<StandardList> selectStandard (int resourceId);
}
