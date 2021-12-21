package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data, int crop_id, String resource_name);
	
	List<Map<String, Object>> selectStandard (int resourceId);

	public int deleteBreed(String breed_id,String breed_row);
	
	public int updateStandardCell(StandardList data);

	int InsertBreedFile(BreedFile breed_file);

	int InsertBreedUpload(Uploads upload);

	List<Breed> SearchBreed(String breed_name);

	String SearchCropName(int breed_name);

	int InsertBreed(Breed breed);

	List<Detail> SelectDetailExcel(int resource_id);

	int InsertExcel(List<StandardList> standards);
}
