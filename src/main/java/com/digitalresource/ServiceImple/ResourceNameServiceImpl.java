package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Feature;
import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Mapper.ResourceNameMapper;
import com.digitalresource.Service.ResourceNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResourceNameServiceImpl implements ResourceNameService {
  @Autowired
  private ResourceNameMapper mapper;

  public int checkDuplicateResourceName(String resource_name, int crop_id) {
    int result = -1;
    result = mapper.getCountResourceNameByCrop(crop_id, resource_name);
    if (result > 0) {
      //Err_cd
      return result;
    }
    return result;
  }

  public int registResourceName(String resource_name) {
    int result = -1;

    ResourceName name = selectResourceName(resource_name);
    if (name != null) {
      return name.getResource_name_id();
    } else {
      ResourceName registParam = new ResourceName(resource_name);
      result = mapper.registResourceName(registParam);
      if (result > 0) {
        return registParam.getResource_name_id();
      }
    }
    return result;
  }

  @Override
  public int deleteResourceName(int resource_name_id) {
    int result = -1;

    int count = mapper.deleteResourceName(resource_name_id);

    return result;
  }

  @Override
  public int registResourceCrop(int crop_id, int resource_name_id) {
    return 0;
  }

  @Override
  public ResourceName selectResourceName(String resource_name) {
    ResourceName name = null;
    name = mapper.selectResourceName(resource_name);

    return name;
  }

  @Override
  public int getCountResourceNameByCrop(String resource_name) {
    return 0;
  }

  @Override
  public int CountResourceNameByCrop(String resource_name) {
    return mapper.CountResourceNameByCrop(resource_name);
  }

  @Override
  public int confirmResourceName(Map<String, Object> param) {
    return mapper.confirmResourceName(param);
  }

  @Override
  public List<Feature> featureHeadList(int feature_group) {
    return mapper.featureHeadList(feature_group);
  }

  @Override
  public int registerResource(Map<String, Object> param) {
    return mapper.registerResource(param);
  }

  @Override
  public int insertResource_name(ResourceName resourceName) {
    return mapper.insertResource_name(resourceName);
  }

  @Override
  public int[] SelectResourceNameId(String resourceName) {
    return mapper.SelectResourceNameId(resourceName);
  }
}