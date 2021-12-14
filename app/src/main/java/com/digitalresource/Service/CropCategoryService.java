package com.digitalresource.Service;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.CropCategory;

import java.util.List;

public interface CropCategoryService {

  List<CropCategory> SelectCropCategoryList();

  List<Crop> SelectCropByCategory(int category_id);

}
