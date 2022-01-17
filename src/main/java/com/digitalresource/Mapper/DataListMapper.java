package com.digitalresource.Mapper;

import com.digitalresource.Entity.DataList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataListMapper {

  List<DataList> SelectDataList(@Param("datalist_type") String datalist_type);

  int InsertDataList(DataList dataList);

  List<Map<String, String>> SelectDateGroup(@Param("resource_id") int resource_id, @Param("resource_name") String datalist_type);

  List<Integer> SelectTarget(@Param("datalist_date") String datalist_date, @Param("resource_name") String resource_name);

  int SelectTargetCount(@Param("datalist_date") String datalist_date);

  int DeleteList(int[] breed_id);

}
