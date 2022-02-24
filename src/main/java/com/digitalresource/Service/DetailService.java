package com.digitalresource.Service;

import com.digitalresource.Entity.ChartCount;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.Resource;

import java.util.List;

public interface DetailService {
  public int registDetails(Resource resource);

  public List<Detail> SelectDetailListByResource(int resource_id);

  ChartCount SelectFileCount(int user_group);
}
