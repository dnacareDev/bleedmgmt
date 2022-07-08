package com.digitalresource.Mapper;

import com.digitalresource.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
    public User LoginUserInfo(String user_username);
    
    int UpdatePassword(User user);
    
}
