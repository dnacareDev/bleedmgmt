package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Entity.ResourceName;

public interface ResourceService {
	public int registResource(Resource resource);

	public int deleteResource(Resource resource);

	public int deleteReourceByCrop(int crop_id);

	public int deleteResourceByCropCategory(int category_id);

	public int insertResource(Resource resource);

	public int selectResourceCount();

	public List<ResourceList> searchResource();

	public int changeResourceUse(Map<String, Object> map);

	public int deleteResource(String resource_id);

	public List<ResourceName> resourceList();

	Integer SearchResourceId(int crop_id, int resource_name_id);

	Integer SelectCropId(int resource_name_id);

	public List<Detail> selectDetailHead(Map<String, Object> param);
}
