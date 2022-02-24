package com.digitalresource.Service;

import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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

	public List<ResourceName> resourceList(int group);

	Integer SearchResourceId(int crop_id, int resource_name_id);

	Integer SelectCropId(int resource_name_id);

	public List<Detail> selectDetailHead(Map<String, Object> param);

	public int detailDisplayAction(Map<String, Object> param);

	int SelectCropCount(String resource_name, String crop_name);

	MonthCount SelectCropMonth(String crop_name, String month, String resource_name);

	Map<String, Object> SelectDetailInfo(String detail_id);

	int SelectResourceUse(int resource_id);

	int UpdateResourceUse(int resource_use, int resource_id);
}
