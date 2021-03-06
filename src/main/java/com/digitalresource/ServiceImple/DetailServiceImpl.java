package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.ChartCount;
import com.digitalresource.Entity.Detail;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Mapper.DetailMapper;
import com.digitalresource.Service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServiceImpl implements DetailService {
  @Autowired
  private DetailMapper mapper;

  @Override
  public int registDetails(Resource resource) {
    int result = -1;
    //result = mapper.registDetails(resource.getResource_id(), resource.getDetailList());

    if (result != resource.getDetailCount()) {
      //Error
    }

    return result;
  }

  @Override
  public List<Detail> SelectDetailListByResource(int resource_id) {
    return mapper.SelectDetailListByResource(resource_id);
  }

  @Override
  public ChartCount SelectFileCount(int user_group) {
    return mapper.SelectFileCount(user_group);
  }
  
  //2022-06-03
  @Override
  public List<Detail> SearchDetailById(int resource_id) {
	  return mapper.SearchDetailById(resource_id);
  }
}
