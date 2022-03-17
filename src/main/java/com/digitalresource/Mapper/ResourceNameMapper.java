package com.digitalresource.Mapper;

import com.digitalresource.Entity.Feature;
import com.digitalresource.Entity.ResourceName;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

@Mapper
public interface ResourceNameMapper {
  //    자원관리 생성 중복검사
  public int getCountResourceNameByCrop(@Param("crop_id") int crop_id,
                                        @Param("resource_name") String resource_name);

  public int CountResourceNameByCrop(@Param("resource_name") String resource_name);

  public ResourceName selectResourceName(@Param("resource_name") String resource_name, @Param("user_group") int user_group);

  public int registResourceName(ResourceName registParam);

  public int deleteResourceName(@Param("resource_name_id") int resource_name_id);

  public int confirmResourceName(Map<String, Object> param);

  public List<Feature> featureHeadList(@Param("feature_group") int feature_group);

  public int registerResource(Map<String, Object> param);

  public int insertResource_name(ResourceName resourceName);

  public int insertResource(Map<String, Object> param);

  public int changeResourceUse(Map<String, Object> map);

  public int[] SelectResourceNameId(@Param("resource_name") String resource_name, @Param("user_group") int user_group);

}