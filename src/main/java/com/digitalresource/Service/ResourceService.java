package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceList;

public interface ResourceService {
    public int registResource(Resource resource);

    public int deleteResource(int resource_id);

    public int deleteReourceByCrop(int crop_id);

    public int deleteResourceByCropCategory(int category_id);

	public int insertResource(Resource resource);

	public int selectResourceCount();

	public List<ResourceList> searchResource();
}
