package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.CropCategory;
import com.digitalresource.Mapper.CropCategoryMapper;
import com.digitalresource.Service.CropCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropCategoryServiceImpl implements CropCategoryService {

  @Autowired
  private CropCategoryMapper mapper;

  @Override
  public List<CropCategory> SelectCropCategoryList() {
    return mapper.SelectCropCategoryList();
  }

  @Override
  public List<Crop> SelectCropByCategory(int category_id) {
    return mapper.SelectCropByCategory(category_id);
  }
}
