package com.digitalresource.Service;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Resource;

public interface ResourceService {
    public int registResource(Resource resource);

    public int deleteResource(int resource_id);

    public int deleteReourceByCrop(int crop_id);

    public int deleteResourceByCropCategory(int category_id);
}
