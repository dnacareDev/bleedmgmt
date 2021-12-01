package com.digitalresource.Service;

public interface ResourceNameService {
    int registResourceName(String resource_name);

    int deleteResourceName(int resource_name_id);

    int registResourceCrop(int crop_id, int resource_name_id);

    int getCountResourceNameByCrop(String resource_name);
}
