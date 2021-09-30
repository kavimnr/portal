package com.portal.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LoginPageFilter
 */
public class LoginPageFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{

		HttpServletRequest req=(HttpServletRequest)request;
		if(req.getSession(false)!=null&&req.getSession(false).getAttribute("username")!=null)
		{
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.forward(request, response);
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
