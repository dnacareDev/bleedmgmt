package com.digitalresource.Service;


import com.digitalresource.Entity.Crop;

import java.util.List;

public interface CropService {
    public int registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResource(int resource_id);

    public Integer deleteCrop(int crop_id);

    public int deleteCropByCategory(int category_id);

    public List<Crop> selectCropByCategory(int category_id);

    public int checkDuplicateCropName(String crop_name);

    public String SelectCropById(int crop_id);

    public List<Crop> SearchCropList(String type, int group);
    
}
