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
	
	@Override
	public int RecordLog(String userIdName, String userName, String log_contents) {
		System.out.println(userIdName);
		System.out.println(userName);
		System.out.println(log_contents);
		return logMapper.RecordLog(userIdName, userName, log_contents);
	}

}
