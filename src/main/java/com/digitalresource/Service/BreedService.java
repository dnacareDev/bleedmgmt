package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalresource.Entity.StandardList;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data);
	
	List<Map<String, Object>> selectStandard (int resourceId);

	public int deleteBreed(String breed_id);
	
	public int updateStandardCell(StandardList data);
	
}
