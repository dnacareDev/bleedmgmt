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

	CountSelect selectDetailCount(@Param("resource_id") int resource_id);

	public int updateStandardCell(StandardList data);
	
	int deleteBreed(@Param("breed_id") String breed_id);

	int deleteStandard(@Param("breed_id") String breed_id);

	int selectResourceId(Map<String, Object> param);

	int InsertBreedFile(BreedFile breed_file);

	int InsertBreedUpload(Uploads upload);

  List<Breed> SearchBreed(@Param("breed_name") String breed_name);

  List<Breed> SearchBreed2(@Param("crop_name") String breed_name, @Param("resource_id") int resource_id);

	String SearchCropName(@Param("crop_id") int breed_name);

	int InsertBreed(Breed breed);

	List<Detail> SelectDetailExcel(@Param("resource_id") int resource_id);

	int InsertExcel(List<StandardList> standards);

	List<StandardList> SelectBreedStandard(@Param("breed_id") int breed_id);

	int deleteStandards(@Param("breed_id") String breed_id);

	int UpdateBreed(@Param("breed_id") int breed_id, @Param("detail_id") int detail_id, @Param("standard") String standard);

	int UpdateAllBreed(List<StandardList> list);


}
