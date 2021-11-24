package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.User;
import com.digitalresource.Mapper.LoginMapper;
import com.digitalresource.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper mapper;

    @Override
    public User loadUserByUsername(String user_username) {
        User user = mapper.LoginUserInfo(user_username);
        return user;
    }
}
