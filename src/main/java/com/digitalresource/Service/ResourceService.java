package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Entity.ResourceName;

public interface ResourceService {
  public int registResource(Resource resource);

  public int deleteResource(int resource_id);

  public int deleteReourceByCrop(int crop_id);

  public int deleteResourceByCropCategory(int category_id);

  public int insertResource(Resource resource);

  public int selectResourceCount();

  public List<ResourceList> searchResource();

  public int changeResourceUse(Map<String, Object> map);

  public List<ResourceName> resourceList();

  Integer SearchResourceId(int crop_id, int resource_name_id);
}
