package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.StandardList;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data);
	
	List<Map<String, Object>> selectStandard (int resourceId);

	public int deleteBreed(String breed_id);
}
