package com.portal.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class LoginFilter implements Filter 
{
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		
		RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
		rd.include(request, response);
		chain.doFilter(request, response);
		
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}
	public void destroy() {
	}
}
