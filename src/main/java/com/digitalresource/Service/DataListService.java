package com.digitalresource.Service;

import com.digitalresource.Entity.DataList;

import java.util.List;
import java.util.Map;

public interface DataListService {

  List<DataList> SelectDataList(String datalist_type);

  int InsertDataList(DataList dataList);

  List<Map<String, String>> SelectDateGroup(String datalist_type);

  List<Integer> SelectTarget(String datalist_date, String datalist_type);

  int SelectTargetCount(String datalist_date);


}
