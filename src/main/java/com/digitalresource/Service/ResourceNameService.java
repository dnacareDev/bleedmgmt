package com.digitalresource.Service;

import com.digitalresource.Entity.ResourceName;

public interface ResourceNameService {
    public int registResourceName(String resource_name, int crop_id);

    public int deleteResourceName(int resource_name_id);

    public int registResourceCrop(int crop_id, int resource_name_id);

    public int deleteResourceCropByResoruceName(int resource_name_id);

    public ResourceName selectResourceName(String resource_name);
}
