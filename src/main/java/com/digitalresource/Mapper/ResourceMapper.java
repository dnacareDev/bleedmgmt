package com.digitalresource.Mapper;

import com.digitalresource.Entity.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface ResourceMapper {
    public int registResource(Resource resourec);

    public int getCountResourceName(int resource_name_id);

    public Resource selectResourceById(int resource_id);

    public int deleteResouceByCropCategory(int crop_id);
    
    int deleteResource(Resource resource);
    
    public int deleteReourceByCrop(int crop_id);

	public int insertResource(Resource resource);

	public int registerDetail(Map<String, Object> map);

	public List<ResourceList> searchResource();

	public int selectResourceCount();

	public int changeResourceUse(Map<String, Object> map);

	public List<ResourceName> resourceList();

	public Integer SearchResourceId(@Param("crop_id") int crop_id, @Param("resource_name_id") int resource_name_id);

	public Integer SelectCropId(@Param("resource_name_id") int resource_name_id);

	public List<Detail> selectDetailHead(Map<String, Object> param);

	public int detailDisplayAction(String detailIds);

	public int detailDisplayAction(Map<String, Object> param);

  int SelectCropCount(String resource_name, String crop_name);

	MonthCount SelectCropMonth(String crop_name, String month, String resource_name);
}
