package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Mapper.CropMapper;
import com.digitalresource.Service.Cropservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropServiceImpl implements Cropservice {
    @Autowired
    private CropMapper mapper;

    @Override
    public boolean registCrop(Crop crop) {
        int result = mapper.registCrop(crop);
        if(result > 0)
            return true;
        return false;
    }

    @Override
    public List<Crop> selectCropList() {
        List<Crop> cropList = mapper.selectCropList();
        return cropList;
    }

    @Override
    public List<Crop> selectCropListByResource(int resource_id) {
        return null;
    }

    @Override
    public int deleteCrop(int crop_id) {
        int result = -1;

        result = mapper.deleteCropById(crop_id);
        if(result > 0){

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
}
