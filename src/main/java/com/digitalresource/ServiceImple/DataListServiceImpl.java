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
  public List<DataList> SelectDataList(String datalist_type) {
    return mapper.SelectDataList(datalist_type);
  }

  @Override
  public int InsertDataList(DataList dataList) {
    return mapper.InsertDataList(dataList);
  }

  @Override
  public List<Map<String, String>> SelectDateGroup(String datalist_type) {
    return mapper.SelectDateGroup(datalist_type);
  }

  @Override
  public  List<Integer> SelectTarget(String datalist_date, String datalist_type) {
    return mapper.SelectTarget(datalist_date, datalist_type);
  }

  @Override
  public int SelectTargetCount(String datalist_date) {
    return mapper.SelectTargetCount(datalist_date);
  }
}
