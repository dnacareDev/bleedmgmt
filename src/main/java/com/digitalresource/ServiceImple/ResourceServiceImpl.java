package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.File;
import com.digitalresource.Entity.Resource;
import com.digitalresource.Mapper.ResourceMapper;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.FileService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private ResourceNameService nameService;

    @Autowired
    private DetailService detailService;

    @Autowired
    private FileService fileService;

    @Override
    public int registResource(Resource resource) {
        int result = -1;
        int resource_name_id = nameService.registResourceName(resource.getResource_name(), resource.getCrop_id());
        if(resource_name_id < 0)
            return result;

        resource.setResource_name_id(resource_name_id);

        //upload Resource File
        fileService.registFile(resource.getResource_template());

        //parse Character to Detail

        //parse Self Character to Detail

        //regist Detail
        result = detailService.registDetails(resource);
        if(result < 0){
            return result;
        }

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

        //사용되지 않는 ResourceName 제거
        //삭제된 Resource 관련 항목들 삭제

        return result;
    }

    @Override
    public int deleteResourceByCropCategory(int category_id) {
        int result = -1;
        result = resourceMapper.deleteResouceByCropCategory(category_id);

        return result;
    }
}
