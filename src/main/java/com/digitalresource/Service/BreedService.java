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

	int insertBreed(int resource_id, String data, int crop_id, String resource_name, int type_check, int group);
	
	List<Map<String, Object>> selectStandard (int resourceId);

	List<StandardList> SelectStandard (int breed_id);

	List<StandardList> SelectStandard2 (int breed_id);

	public int deleteBreed(String breed_id);
	
	public int updateStandardCell(StandardList data);

	int DeleteBreedFile(int breed_file_id);
	
	int InsertBreedFile(BreedFile breed_file);

	// 첨부파일 목록 조회
	List<BreedFile> SelectBreedFile(int breed_id, int file_type);

	List<BreedFileSimple> IsMatchBreedFile();
	
	// 첨부파일 내용 수정
	int UpdateBreedFile(BreedFile breed_file);

	// 첨부파일 수정
	int UpdateBreedUpload(Uploads upload);

	int InsertBreedUpload(Uploads upload);

	List<Breed> SearchBreed(String breed_name);

	List<Breed> SearchBreed2(String breed_name, int resource_id);

	List<Breed> SearchBreed3(int resource_id);
	
	int[] SearchFilledBreed(String crop_name, int resourceId);

	Breed SearchBreedById(int target_id);

	String SearchCropName(int breed_name);

	int InsertBreed(Breed breed);

	List<Detail> SelectDetailExcel(int resource_id);

	List<Detail> SelectDetailExcel2(int resource_id);

	int InsertExcel(List<StandardList> standards);

	List<StandardList> SelectBreedStandard(int breed_id);
	
	List<StandardList> SelectBreedStandard2(int breed_id);

	int UpdateBreed(int breed_id, int detail_id, String standard);

	int UpdateAllBreed(List<StandardList> list);
	
	List<StandardList> SearchStandardByCellValue(String cell_value);
	
	List<StandardList> DatabaseSelect(String crop_name, int resourceId);
}
