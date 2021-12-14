package com.digitalresource.Config.Security;

import com.digitalresource.Entity.User;
import com.digitalresource.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.digitalresource.Entity.User;
import com.digitalresource.Service.LoginService;


@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginService service;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        User user = service.loadUserByUsername(authentication.getName());
        if(user == null)
        {
            throw new UsernameNotFoundException("해당 아이디가 없습니다.");
        }

        if(!passwordEncoder.matches(authentication.getCredentials().toString(),user.getUser_password()) || user == null)
        {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return true;
    }
}
