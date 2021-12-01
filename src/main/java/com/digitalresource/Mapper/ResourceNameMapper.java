package com.digitalresource.Mapper;

import com.digitalresource.Entity.ResourceName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ResourceNameMapper {
//    자원관리 생성 중복검사
    public int getCountResourceNameByCrop(@Param("resource_name") String resource_name);

    public ResourceName selectResourceName(String resource_name);

    public int registResourceName(ResourceName registParam);

    public int deleteResourceName(int resource_name_id);
}
