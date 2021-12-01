package com.digitalresource.Service;

import com.digitalresource.Entity.ResourceName;
import org.apache.ibatis.annotations.Param;

public interface ResourceNameService {
    int registResourceName(String resource_name);

    int CountResourceNameByCrop(String resource_name);

    int deleteResourceName(int resource_name_id);

    int registResourceCrop(int crop_id, int resource_name_id);

    ResourceName selectResourceName(String resource_name);

    int getCountResourceNameByCrop(String resource_name);
}
