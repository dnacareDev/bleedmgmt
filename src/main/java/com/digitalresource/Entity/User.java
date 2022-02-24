package com.digitalresource.Entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class User implements UserDetails
{
    private int user_id;					// 사용자 고유번호
    private String user_username;			// 사용자 아이디
    private String user_password;			// 사용자 비밀번호
    private String user_name;				// 사용자 이름
    private int user_type;					// 사용자 유형
    private String user_authority;			// 사용자 권한(spring security)
    private String create_date;				// 등록일
    private String modify_date;				// 수정일
    private int user_group;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user_authority));
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return this.user_password;
    }

    @Override
    public String getUsername()
    {
        return this.getUser_username();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }
}
