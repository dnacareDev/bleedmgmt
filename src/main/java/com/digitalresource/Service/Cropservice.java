package com.digitalresource.Service;


import com.digitalresource.Entity.Crop;

import java.util.List;

public interface Cropservice {
    public boolean registCrop(Crop crop);

    public List<Crop> selectCropList();

    public List<Crop> selectCropListByResource(int resource_id);
}
