package com.digitalresource.Mapper;

import com.digitalresource.Entity.Crop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropMapper {
    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResource(int resource_id);

    public int deleteCropById(int crop_id);

    public int deleteCropByCategory(int category_id);

    public List<Crop> selectCropByCategory(int category_id);
}
