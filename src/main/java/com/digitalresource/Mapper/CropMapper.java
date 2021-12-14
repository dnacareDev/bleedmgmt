package com.digitalresource.Mapper;

import com.digitalresource.Entity.Crop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CropMapper {
    public int checkDuplicateCropName(String crop_name);

    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResourceName(int resource_name_id);

    public int deleteCropById(int crop_id);

    public int deleteCropByCategory(int category_id);

    public List<Crop> selectCropByCategory(int category_id);

    public String SelectCropById(@Param("crop_id") int crop_id);

    public List<Crop> SearchCropList(@Param("type") String type);
}
