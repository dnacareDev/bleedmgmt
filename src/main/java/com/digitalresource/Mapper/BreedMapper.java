package com.digitalresource.Mapper;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface BreedMapper {

	int insertBreed(Breed breed);

	int insertStandard(Map<String, Object> map);

	List<StandardList> selectStandard(int resourceId);

	CountSelect selectDetailCount(int resource_id);

	public int updateStandardCell(StandardList data);
	
	int deleteBreed(String breed_id);

	int deleteStandard(String breed_id);

	int selectResourceId(Map<String, Object> param);

	int InsertBreedFile(BreedFile breed_file);

	int InsertBreedUpload(Uploads upload);

  List<Breed> SearchBreed(String breed_name);

	String SearchCropName(int breed_name);
}
