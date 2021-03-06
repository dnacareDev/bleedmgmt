package com.digitalresource.Service;

import com.digitalresource.Entity.DataList;

import java.util.List;
import java.util.Map;

public interface DataListService {

  List<DataList> SelectDataList(int user_group);

  int InsertDataList(DataList dataList);

  List<Map<String, String>> SelectDateGroup(int resource_id, String datalist_type);

  List<Integer> SelectTarget(String datalist_date, String resource_name, int resource_id);

  int SelectTargetCount(String datalist_date);

  int DeleteList(int[] breed_id);

}
