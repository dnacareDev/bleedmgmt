package com.digitalresource.Mapper;

import com.digitalresource.Entity.Feature;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeatureMapper {

	int seedResourcesInsert(Feature feature);

	int selectFeatureGroup();
}