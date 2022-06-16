package com.digitalresource.ServiceImple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalresource.Entity.LogList;
import com.digitalresource.Mapper.LogMapper;
import com.digitalresource.Service.LogService;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;
	
	@Override
	public List<LogList> searchLog() {
		return logMapper.searchLog();
	}

}
