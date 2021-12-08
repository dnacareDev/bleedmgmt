package com.digitalresource.Service;

import com.digitalresource.Entity.Feature;
import com.digitalresource.Entity.ResourceName;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ResourceNameService {
    int registResourceName(String resource_name);

    int CountResourceNameByCrop(String resource_name);

    int deleteResourceName(int resource_name_id);

    int registResourceCrop(int crop_id, int resource_name_id);

    ResourceName selectResourceName(String resource_name);

    int getCountResourceNameByCrop(String resource_name);

	int confirmResourceName(Map<String, Object> param);

	List<Feature> featureHeadList(int feature_group);

	int insertResource_name(String resource_name);
}
