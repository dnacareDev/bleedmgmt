package com.digitalresource.Mapper;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface BreedMapper {

	int insertBreed(Breed breed);

	int insertStandard(Map<String, Object> map);

	List<StandardList> selectStandard(@Param("resource_id") int resourceId);

	List<StandardList> SelectStandard (@Param("breed_id") int breed_id);

	List<StandardList> SelectStandard2 (@Param("breed_id") int breed_id);

	CountSelect selectDetailCount(@Param("resource_id") int resource_id);

	public int updateStandardCell(StandardList data);
	
	int deleteBreed(@Param("breed_id") String breed_id);

	int deleteStandard(@Param("breed_id") String breed_id);

	int selectResourceId(Map<String, Object> param);

	List<BreedFile> SelectBreedFile(@Param("breed_id") int breed_id, @Param("file_type") int file_type);
	
	List<BreedFileSimple> IsMatchBreedFile();

	int InsertBreedFile(BreedFile breed_file);
	
	int DeleteBreedFile(int breed_file_id);

	int InsertBreedUpload(Uploads upload);

	// 첨부파일 내용 수정
	int UpdateBreedFile(BreedFile breed_file);

	// 첨부파일 수정
	int UpdateBreedUpload(Uploads upload);

  List<Breed> SearchBreed(@Param("crop_name") String breed_name);

  List<Breed> SearchBreed2(@Param("crop_name") String breed_name, @Param("resource_id") int resource_id);

  List<Breed> SearchBreed3(@Param("resource_id") int resource_id);
  
  int[] SearchFilledBreed(@Param("crop_name") String crop_name, @Param("resourceId") int resourceId);

  Breed SearchBreedById(@Param("target_id") int target_id);

	String SearchCropName(@Param("crop_id") int breed_name);

	int InsertBreed(Breed breed);

	List<Detail> SelectDetailExcel(@Param("resource_id") int resource_id);

	List<Detail> SelectDetailExcel2(@Param("resource_id") int resource_id);

	int InsertExcel(List<StandardList> standards);

	List<StandardList> SelectBreedStandard(@Param("breed_id") int breed_id);

	List<StandardList> SelectBreedStandard2(@Param("breed_id") int breed_id);

	int deleteStandards(@Param("breed_id") String breed_id);

	int UpdateBreed(@Param("breed_id") int breed_id, @Param("detail_id") int detail_id, @Param("standard") String standard);

	int UpdateAllBreed(List<StandardList> list);

	List<StandardList> SearchStandardByCellValue(String cell_value);
	
	List<StandardList> DatabaseSelect(@Param("crop_name") String crop_name, @Param("resourceId") int resourceId);
}
