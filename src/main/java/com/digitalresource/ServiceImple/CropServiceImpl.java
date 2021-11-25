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
        return null;
    }

    @Override
    public List<Crop> selectCropListByResource(int resource_id) {
        return null;
    }
}
