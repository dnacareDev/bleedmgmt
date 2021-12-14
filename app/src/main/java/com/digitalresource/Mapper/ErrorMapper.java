package com.digitalresource.Mapper;

import com.digitalresource.Entity.DBErrorMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ErrorMapper {
    public DBErrorMessage getErrorMessage(int err_cd);
}
