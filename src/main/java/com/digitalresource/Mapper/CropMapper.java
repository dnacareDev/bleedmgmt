package com.digitalresource.Mapper;

import com.digitalresource.Entity.Crop;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface CropMapper {
    public int checkDuplicateCropName(@Param("crop_name") String crop_name);

    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResourceName(@Param("resource_name_id") int resource_name_id);

    public Integer deleteCropById(@Param("crop_id") int crop_id);

    public int deleteCropByCategory(@Param("category_id") int category_id);

    public List<Crop> selectCropByCategory(@Param("category_id") int category_id);

    public String SelectCropById(@Param("crop_id") int crop_id);
 
    public List<Crop> SearchCropList(@Param("user_type") int user_type, @Param("user_name") String user_name, @Param("type") String type, @Param("group") int group);
    
    public List<Crop> searchAllCrops();
    
}
