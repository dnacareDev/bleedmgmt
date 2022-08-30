package com.digitalresource.Service;


import java.util.List;

import com.digitalresource.Entity.Crop;

public interface CropService {
    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResource(int resource_id);

    public Integer deleteCrop(int crop_id);

    public int deleteCropByCategory(int category_id);

    public List<Crop> selectCropByCategory(int category_id);

    public int checkDuplicateCropName(String crop_name);

    public String SelectCropById(int crop_id);

    public List<Crop> SearchCropList(int user_type, String user_name, String type, int group);
    
    public List<Crop> searchAllCrops();
    
}
