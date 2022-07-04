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

  public int getCountResourceName(@Param("resource_name_id") int resource_name_id);

  public Resource selectResourceById(@Param("resource_id") int resource_id);

  public int deleteResouceByCropCategory(@Param("crop_id") int crop_id);

  int deleteResource(Resource resource);

  public int deleteReourceByCrop(@Param("crop_id") int crop_id);

  public int insertResource(Resource resource);

  public int registerDetail(Map<String, Object> map);

  public List<ResourceList> searchResource(@Param("user_group") int user_group);
  public List<ResourceAllList> searchResourceAll(@Param("crop_id") int crop_id);

  public int selectResourceCount(@Param("user_group") int user_group);

  public int changeResourceUse(Map<String, Object> map);

  public List<ResourceName> resourceList(@Param("group") int group);

  public Integer SearchResourceId(@Param("crop_id") int crop_id, @Param("resource_name_id") int resource_name_id, @Param("user_group") int user_group);

  public Integer SelectCropId(@Param("resource_name_id") int resource_name_id);

  public List<Detail> selectDetailHead(Map<String, Object> param);

  public int detailDisplayAction(@Param("detailIds") String detailIds);
 
  public int detailDisplayAction(Map<String, Object> param);

  int SelectCropCount(@Param("resource_name") String resource_name, @Param("crop_name") String crop_name, @Param("user_group") int user_group);

  MonthCount SelectCropMonth(@Param("crop_name") String crop_name, @Param("month") String month, @Param("resource_name") String resource_name, @Param("user_group") int user_group);

  Map<String, Object> SelectDetailInfo(String detail_id);

  int SelectResourceUse(@Param("resource_id") int resource_id);

  int UpdateResourceUse(@Param("resource_use") int resource_use, @Param("resource_id") int resource_id);
  
  int UpdateDeleteCheck(int resource_id); 												// 2022-06-07 | 삭제처리(delete_check)
  
  // 2022-06-23 | 삭제처리(delete)
  public int deleteResourceNameById(int resourceId);
  public int deleteBreedById(int resourceId);
  public int deleteResourceById(int resourceId);
}
