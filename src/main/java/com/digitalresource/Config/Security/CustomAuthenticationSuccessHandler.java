package com.digitalresource.Config.Security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    private String defaultUrl = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        SimpleGrantedAuthority authroAuthority = (SimpleGrantedAuthority) ((List<GrantedAuthority>)authentication.getAuthorities()).get(0);

        switch (authroAuthority.getAuthority())
        {
            case "USER":
                defaultUrl = "/login_success";
                break;
            case "EXPERT":
                break;
            case "ADMIN":
                break;
        }

        response.setStatus(200);
        response.getWriter().print("success");
        response.sendRedirect(defaultUrl);
//		response.getWriter().print("login");
//		response.getWriter().flush();
//		request.getSession().setAttribute("result", "true");
//		response.sendRedirect(defaultUrl);
    }

    public void setDefaultUrl(String defaultUrl)
    {
        this.defaultUrl = defaultUrl;
    }
}