package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public interface BreedService {
	
	public int insertStandard();

	int insertBreed(int resource_id, String data, int crop_id, String resource_name);
	
	List<Map<String, Object>> selectStandard (int resourceId);

	public int deleteBreed(String breed_id);
	
	public int updateStandardCell(StandardList data);

	int InsertBreedFile(BreedFile breed_file);

	int InsertBreedUpload(Uploads upload);

	List<Breed> SearchBreed(String breed_name);

	List<Breed> SearchBreed2(String breed_name, int resource_id);

	String SearchCropName(int breed_name);

	int InsertBreed(Breed breed);

	List<Detail> SelectDetailExcel(int resource_id);

	int InsertExcel(List<StandardList> standards);

  List<StandardList> SelectBreedStandard(int breed_id);

	int UpdateBreed(int breed_id, int detail_id, String standard);

	int UpdateAllBreed(List<StandardList> list);
}
