package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Mapper.ResourceNameMapper;
import com.digitalresource.Service.ResourceNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceNameServiceImple implements ResourceNameService {
    @Autowired
    private ResourceNameMapper mapper;

    public int registResourceName(String resource_name){
        int result = -1;
        ResourceName name = mapper.selectResourceName(resource_name);
        if(name != null){
            return name.getResource_name_id();
        }
        else{
            ResourceName registParam = new ResourceName(resource_name);
            result = mapper.registResourceName(registParam);
            if(result > 0){
                return registParam.getResource_name_id();
            }
        }
        return result;
    }

    @Override
    public int deleteResourceName(int resource_name_id) {
        int result = -1;

        int count = mapper.deleteResourceName(resource_name_id);
        if(count > 0)
            result = mapper.deleteReourceCropByResourceName(resource_name_id);

        return result;
    }

    @Override
    public int registResourceCrop(int crop_id, int resource_name_id) {
        int result = -1;

        result = mapper.registResourceCropName(crop_id,resource_name_id);

        return result;
    }

  @Override
  public int getCountResourceNameByCrop(String resource_name) {
    return mapper.getCountResourceNameByCrop(resource_name);
  }
}
