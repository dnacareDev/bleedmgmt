package com.digitalresource.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.digitalresource.Entity.Breed;
import com.digitalresource.Entity.CountSelect;
import com.digitalresource.Entity.StandardList;

@Mapper
public interface BreedMapper {

	int insertBreed(Breed breed);

	int insertStandard(Map<String, Object> map);

	List<StandardList> selectStandard(int resourceId);

	CountSelect selectDetailCount(int resource_id);

	public int updateStandardCell(StandardList data);
	
	int deleteBreed(String breed_id);

	int deleteStandard(String breed_id);
}
