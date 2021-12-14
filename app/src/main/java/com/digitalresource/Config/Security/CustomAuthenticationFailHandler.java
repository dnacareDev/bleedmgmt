package com.digitalresource.Config.Security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler
{
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {
        if (exception instanceof AuthenticationServiceException)
        {
            request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");
        }
        else if(exception instanceof BadCredentialsException)
        {
            request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");
        }
        else if(exception instanceof LockedException)
        {
            request.setAttribute("loginFailMsg", "잠긴 계정입니다..");
        }
        else if(exception instanceof DisabledException)
        {
            request.setAttribute("loginFailMsg", "비활성화된 계정입니다..");
        }
        else if(exception instanceof AccountExpiredException)
        {
            request.setAttribute("loginFailMsg", "만료된 계정입니다..");
        }
        else if(exception instanceof CredentialsExpiredException)
        {
            request.setAttribute("loginFailMsg", "비밀번호가 만료되었습니다.");
        }

        // 로그인 페이지로 다시 포워딩
//		RequestDispatcher dispatcher = request.getRequestDispatcher("users/login");
//		dispatcher.forward(request, response);
//		System.out.println("test fail");
        response.sendRedirect("/login/loginFail");

		/*
		System.out.println("fail");

		String returnUrl = "/users/login";

		String returnUrlParam = request.getParameter("returnUrl");
		if (returnUrlParam != null)
			returnUrl = returnUrlParam;

		String errMsg = "";
		if (exception instanceof UsernameNotFoundException)
		{
			errMsg = "userID";
		}
		else if (exception instanceof BadCredentialsException)
		{
			errMsg = "password";
		}

		returnUrl += "?errMsg=" + errMsg;

//		response.sendRedirect(returnUrl);

		response.setStatus(409);
		response.getWriter().print(errMsg);
		response.getWriter().flush();
		*/
    }
}