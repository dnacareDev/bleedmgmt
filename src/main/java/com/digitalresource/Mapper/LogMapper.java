package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalresource.Entity.LogList;

@Mapper
public interface LogMapper {
	
	public List<LogList> searchLog();
	
	int RecordLog(@Param("userIdName") String userIdName, @Param("userName") String userName, @Param("log_contents") String log_contents);

}
