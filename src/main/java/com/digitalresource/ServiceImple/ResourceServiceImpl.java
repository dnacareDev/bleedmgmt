package com.digitalresource.ServiceImple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.Resource;
import com.digitalresource.Entity.ResourceList;
import com.digitalresource.Mapper.ResourceMapper;
import com.digitalresource.Service.DetailService;
import com.digitalresource.Service.FileService;
import com.digitalresource.Service.ResourceNameService;
import com.digitalresource.Service.ResourceService;

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

        //upload Resource Template File
        //result = fileService.registFile(resource.getResource_template());
        if(result < 0){
            return result;
        }
        resource.setResource_template_id(resource.getResource_template_id());

        int resource_name_id = nameService.registResourceName(resource.getResource_name());
        if(resource_name_id < 0)
            return result;

        resource.setResource_name_id(resource_name_id);

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
        Resource resource = resourceMapper.selectResourceById(resource_id);
        int count = resourceMapper.getCountResourceName(resource.getResource_id());
        if(count == 0)
            nameService.deleteResourceName(resource.getResource_name_id());
        result = fileService.deleteFile(resource.getResource_template_id());
        if(result < 0){
            //Err
            return result;
        }

        /*
        * Delete Resource_detail
        * Delete Resource_breed
        * Delete Resource_standard
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
    

	@Override
	public int insertResource(Resource resource) {
		Map<String, Object> map = new HashMap<String, Object>();
		// inert resource
		int resultResource = resourceMapper.insertResource(resource);
		// insert detail
		String jsonArr = resource.getDetailList();
		JSONArray arr = new JSONArray(jsonArr);
		map.put("resource_id", resultResource);
		int result = 0;
		for(int i = 0; i<arr.length();  i++) {
			JSONObject jsonObject = arr.getJSONObject(i);
			map.put("detail_name", jsonObject.get("name"));
			map.put("detail_type", jsonObject.get("type"));
			map.put("detail_info", jsonObject.get("info"));
			map.put("detail_index", i+1);
			result = resourceMapper.registerDetail(map);
			if( result == 0) {
				return 0;
			}
		}
		return result;
	}

	@Override
	public int selectResourceCount() {
		return resourceMapper.selectResourceCount();
	}

	@Override
	public List<ResourceList> searchResource() {
		return resourceMapper.searchResource();
	}
}
