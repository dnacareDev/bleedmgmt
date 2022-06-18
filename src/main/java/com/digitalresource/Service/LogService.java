package com.digitalresource.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalresource.Entity.LogList;

@Service
public interface LogService {

	List<LogList> searchLog();
	
	int RecordLog(String userIdName, String userName, String log_contents);
}
