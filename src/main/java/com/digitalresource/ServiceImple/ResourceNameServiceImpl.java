package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.ResourceName;
import com.digitalresource.Mapper.ResourceNameMapper;
import com.digitalresource.Service.ResourceNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceNameServiceImpl implements ResourceNameService {
    @Autowired
    private ResourceNameMapper mapper;

    public int checkDuplicateResourceName(String resource_name, int crop_id){
        int result = -1;
        result = mapper.getCountResourceNameByCrop(crop_id, resource_name);
        if(result > 0){
            //Err_cd
            return result;
        }
        return result;
    }

    public int registResourceName(String resource_name,int crop_id){
        int result = -1;

        result = checkDuplicateResourceName(resource_name, crop_id);
        if(result < 0){
            return result;
        }

        ResourceName name = selectResourceName(resource_name);
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
        if(count > 0){

        }
        return result;
    }

    @Override
    public ResourceName selectResourceName(String resource_name) {
        ResourceName name = null;
        name = mapper.selectResourceName(resource_name);

        return name;
    }

  @Override
  public int getCountResourceNameByCrop(String resource_name) {
    return mapper.getCountResourceNameByCrop(resource_name);
  }
}