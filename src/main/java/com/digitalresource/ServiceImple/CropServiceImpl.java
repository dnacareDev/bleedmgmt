package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Mapper.CropMapper;
import com.digitalresource.Service.CropService;
import com.digitalresource.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements CropService {
    @Autowired
    private CropMapper mapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public int registCrop(Crop crop) {
        int result = -1;

        result = checkDuplicateCropName(crop.getCrop_name());
        if(result < 0)
            return result;

        result = mapper.registCrop(crop);
        if(result > 0)
            return result;

        return -1001;
    }

    @Override
    public List<Crop> selectCropList() {
        List<Crop> cropList = mapper.selectCropList();
        return cropList;
    }

    @Override
    public List<Crop> selectCropListByResource(int resource_id) {
        List<Crop> cropList = null;
        cropList = mapper.selectCropListByResourceName(resource_id);
        return cropList;
    }

    @Override
    public int deleteCrop(int crop_id) {
        int result = -1;

        result = mapper.deleteCropById(crop_id);
        if(result > 0){
            resourceService.deleteReourceByCrop(crop_id);
        }

        return result;
    }

    @Override
    public int deleteCropByCategory(int category_id) {
        int result = -1;
        result = mapper.deleteCropByCategory(category_id);
        if(result > 0){

        }
        return result;
    }

    @Override
    public List<Crop> selectCropByCategory(int category_id) {
        List<Crop> list = mapper.selectCropByCategory(category_id);
        return list;
    }

    @Override
    public int checkDuplicateCropName(String crop_name){
        int result = -1;
        result = mapper.checkDuplicateCropName(crop_name);
        if(result > 0){
            return -1000;
        }
        return result;
    }
}
