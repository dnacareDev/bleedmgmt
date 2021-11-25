package com.digitalresource.Mapper;

import com.digitalresource.Entity.Crop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CropMapper {
    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResource(int resource_id);
}
