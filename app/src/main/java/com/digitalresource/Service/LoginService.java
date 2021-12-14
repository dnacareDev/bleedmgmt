package com.digitalresource.Service;


import com.digitalresource.Entity.User;

public interface LoginService {
    User loadUserByUsername(String user_username);
}
