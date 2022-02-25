package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.DataList;
import com.digitalresource.Mapper.DataListMapper;
import com.digitalresource.Service.DataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataListServiceImpl implements DataListService {

  @Autowired
  private DataListMapper mapper;

  @Override
  public List<DataList> SelectDataList(int user_group) {
    return mapper.SelectDataList(user_group);
  }

  @Override
  public int InsertDataList(DataList dataList) {
    return mapper.InsertDataList(dataList);
  }

  @Override
  public List<Map<String, String>> SelectDateGroup(int resource_id, String datalist_type) {
    return mapper.SelectDateGroup(resource_id, datalist_type);
  }

  @Override
  public  List<Integer> SelectTarget(String datalist_date, String resource_name, int resource_id) {
    return mapper.SelectTarget(datalist_date, resource_name, resource_id);
  }

  @Override
  public int SelectTargetCount(String datalist_date) {
    return mapper.SelectTargetCount(datalist_date);
  }

  @Override
  public int DeleteList(int[] breed_id) {
    return mapper.DeleteList(breed_id);
  }
}
