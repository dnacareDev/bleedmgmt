package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.Resource;
import com.digitalresource.Mapper.ResourceMapper;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceNameService nameService;

    @Override
    public int registResource(Resource resource) {
        int result = -1;
        int resource_name_id = nameService.registResourceName(resource.getResource_name(), resource.getCrop_id());
        if(resource_name_id < 0)
            return result;

        resource.setResource_name_id(resource_name_id);

        return result;
    }

    @Override
    public int deleteResource(int resource_id) {
        int result = -1;
        Resource deleteParam = resourceMapper.selectResourceById(resource_id);
        int count = resourceMapper.getCountResourceName(deleteParam.getResource_id());
        if(count == 0)
            nameService.deleteResourceName(deleteParam.getResource_name_id());

        /*
        * Delete Resource_detail
        * Delete character 삭제
        * Delete Resource_row 삭제
        * Delete Resource_standard 삭제
        * */

        return result;
    }

    @Override
    public int deleteReourceByCrop(int crop_id) {
        int result = -1;
        result = resourceMapper.deleteReourceByCrop(crop_id);
        return result;
    }

    @Override
    public int deleteResourceByCropCategory(int category_id) {
        int result = -1;
        result = resourceMapper.deleteResouceByCropCategory(category_id);

        return result;
    }
}
