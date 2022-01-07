package com.digitalresource.Mapper;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.CropCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CropCategoryMapper {

  List<CropCategory> SelectCropCategoryList();

  List<Crop> SelectCropByCategory(@Param("category_id") int category_id);
}
