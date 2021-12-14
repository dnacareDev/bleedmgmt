package com.digitalresource.Controller;

import com.digitalresource.Entity.DBErrorMessage;
import com.digitalresource.Service.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DBErrorController {
    @Autowired
    ErrorService errorService;

    @GetMapping("/error-message")
    @ResponseBody
    public DBErrorMessage getErrorMessage(@RequestParam int err_cd){
        DBErrorMessage errorMessage = errorService.getErrorMessage(err_cd);

        return errorMessage;
    }
}
