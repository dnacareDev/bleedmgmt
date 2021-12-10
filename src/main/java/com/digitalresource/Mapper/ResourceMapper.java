package com.digitalresource.Mapper;

import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceList;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceMapper {
    public int registResource(Resource resourec);

    public int getCountResourceName(int resource_name_id);

    public Resource selectResourceById(int resource_id);

    public int deleteResouceByCropCategory(int crop_id);

    public int deleteReourceByCrop(int crop_id);

	public int insertResource(Resource resource);

	public int registerDetail(Map<String, Object> map);

	public List<ResourceList> searchResource();

	public int selectResourceCount();

	public int changeResourceUse(Map<String, Object> map);
}
