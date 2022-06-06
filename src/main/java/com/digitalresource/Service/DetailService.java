package com.digitalresource.Service;

import java.util.List;

import com.digitalresource.Entity.ChartCount;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.Resource;

public interface DetailService {
  public int registDetails(Resource resource);

  public List<Detail> SelectDetailListByResource(int resource_id);

  ChartCount SelectFileCount(int user_group);

  public List<Detail> SearchDetailById(int resource_id);						// 2022-06-03		

}
