package com.digitalresource.Service;

public interface ResourceNameService {
    public int registResourceName(String resource_name);

    public int deleteResourceName(int resource_name_id);

    public int registResourceCrop(int crop_id, int resource_name_id);
}
