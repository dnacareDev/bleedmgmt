package com.digitalresource.Service;

import com.digitalresource.Entity.DBErrorMessage;
import org.springframework.stereotype.Service;

public interface ErrorService {
    public DBErrorMessage getErrorMessage(int err_cd);
}
