package com.digitalresource.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.digitalresource.Entity.LogList;

@Mapper
public interface LogMapper {
	
	public List<LogList> searchLog();

}
