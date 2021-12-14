package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.DBErrorMessage;
import com.digitalresource.Mapper.ErrorMapper;
import com.digitalresource.Service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorServiceImpl implements ErrorService {
    @Autowired
    private ErrorMapper mapper;

    @Override
    public DBErrorMessage getErrorMessage(int err_cd) {
        DBErrorMessage errorMessage = mapper.getErrorMessage(err_cd);
        return errorMessage;
    }
}
