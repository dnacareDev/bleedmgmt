package com.digitalresource.ServiceImple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalresource.Entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Mapper.ResourceMapper;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.FileService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {
  @Autowired
  private ResourceMapper resourceMapper;

  @Autowired
  private ResourceNameService nameService;

  @Autowired
  private DetailService detailService;

  @Autowired
  private FileService fileService;

  @Override
  public int registResource(Resource resource) {
    int result = -1;

    //upload Resource Template File
    //result = fileService.registFile(resource.getResource_template());
    if (result < 0) {
      return result;
    }
    resource.setResource_template_id(resource.getResource_template_id());

    int resource_name_id = nameService.registResourceName(resource.getResource_name(), resource.getUser_group());
    if (resource_name_id < 0)
      return result;

    resource.setResource_name_id(resource_name_id);

    //parse Character to Detail

    //parse Self Character to Detail

    //regist Detail
    result = detailService.registDetails(resource);
    if (result < 0) {
      return result;
    }

    return result;
  }

  @Override
  public int deleteResource(Resource resource) {
    return resourceMapper.deleteResource(resource);
  }

  @Override
  public int deleteReourceByCrop(int crop_id) {
    int result = -1;
    result = resourceMapper.deleteReourceByCrop(crop_id);

    //사용되지 않는 ResourceName 제거
    //삭제된 Resource 관련 항목들 삭제

    return result;
  }

  @Override
  public int deleteResourceByCropCategory(int category_id) {
    int result = -1;
    result = resourceMapper.deleteResouceByCropCategory(category_id);

    return result;
  }


  @Override
  public int insertResource(Resource resource) {
    Map<String, Object> map = new HashMap<String, Object>();
    // insert resource
    int resultResource = resourceMapper.insertResource(resource);
    // insert detail
    String jsonArr = resource.getDetailList();
    JSONArray arr = new JSONArray(jsonArr);
    int group = resource.getUser_group();

    map.put("resource_id", resource.getResource_id());
    
    int result = 0;

    for (int i = 0; i < arr.length(); i++) {
      JSONObject jsonObject = arr.getJSONObject(i);

      map.put("detail_name", jsonObject.get("name"));
      map.put("detail_type", jsonObject.get("type"));
      map.put("detail_info", jsonObject.get("info"));
      map.put("detail_check", jsonObject.get("check"));
      map.put("detail_click", jsonObject.get("click"));
      map.put("detail_index", i + 1);
      map.put("user_group", group);
      
      result = resourceMapper.registerDetail(map);
      
      if (result == 0) {
        return 0;
      }
    }

    return result;
  }

  @Override
  public int selectResourceCount(int user_group) {
    return resourceMapper.selectResourceCount(user_group);
  }

  @Override
  public List<ResourceList> searchResource(int user_group) {
    return resourceMapper.searchResource(user_group);
  }
  
  @Override
  public List<ResourceAllList> searchResourceAll(int crop_id) {
	  return resourceMapper.searchResourceAll(crop_id);
  }

  @Override
  public int changeResourceUse(Map<String, Object> map) {
    return resourceMapper.changeResourceUse(map);
  }

  @Override
  public List<ResourceName> resourceList(int group) {
    return resourceMapper.resourceList(group);
  }

  @Override
  public Integer SearchResourceId(int crop_id, int resource_name_id, int user_group) {
    return resourceMapper.SearchResourceId(crop_id, resource_name_id, user_group);
  }

  @Override
  public Integer SelectCropId(int resource_name_id) {
    return resourceMapper.SelectCropId(resource_name_id);
  }
  
  @Override
  public int deleteResource(String resource_id) {
  // TODO Auto-generated method stub
  return 0;
  }

@Override
public List<Detail> selectDetailHead(Map<String, Object> param) {
	return resourceMapper.selectDetailHead(param);
}

@Override
public int detailDisplayAction(Map<String, Object> param) {
	return resourceMapper.detailDisplayAction(param);
}

  @Override
  public int SelectCropCount(String resource_name, String crop_name, int user_group) {
    return resourceMapper.SelectCropCount(resource_name, crop_name, user_group);
  }

  /*
  @Override
  public MonthCount SelectCropMonth(String crop_name, String month, String resource_name, int user_group) {
    return resourceMapper.SelectCropMonth(crop_name, month, resource_name, user_group);
  }
  */
  
  @Override
  public MonthCount SelectCropMonth(String crop_name, String month, int year, String resource_name, int user_group) {
	  return resourceMapper.SelectCropMonth(crop_name, month, year, resource_name, user_group);
  }
  
  @Override
  public YearCount SelectCropYear(String crop_name, String year, String resource_name, int user_group) {
	  return resourceMapper.SelectCropYear(crop_name, year, resource_name, user_group);
  }

  @Override
  public Map<String, Object> SelectDetailInfo(String detail_id) {
    return resourceMapper.SelectDetailInfo(detail_id);
  }

  @Override
  public int SelectResourceUse(int resource_id) {
    return resourceMapper.SelectResourceUse(resource_id);
  }

  @Override
  public int UpdateResourceUse(int resource_use, int resource_id) {
    return resourceMapper.UpdateResourceUse(resource_use, resource_id);
  }
  
  // 2022-06-02
  @Override
  public Resource selectResourceById(int resource_id) {
	  return resourceMapper.selectResourceById(resource_id);
  }
  
  // 2022-06-07
  @Override
  public int UpdateDeleteCheck(int resource_id) {
	  return resourceMapper.UpdateDeleteCheck(resource_id);
  }
  
  // 2022-06-23 | 삭제처리(delete)
  @Override
  public int deleteResourceNameById(int resourceId) {
	  return resourceMapper.deleteResourceNameById(resourceId);
  }
  @Override
  public int deleteBreedById(int resourceId) {
	  return resourceMapper.deleteBreedById(resourceId);
  }
  @Override
  public int deleteResourceById(int resourceId) {
	  return resourceMapper.deleteResourceById(resourceId);
  }
  
  @Override
  public int searchResourceCount(int resourceId) {
	  return resourceMapper.searchResourceCount(resourceId);
  }
  
  @Override 
  public int SelectCropIdByResourceId(int resourceId) {
	  return resourceMapper.SelectCropIdByResourceId(resourceId);
  }
  
  
}
