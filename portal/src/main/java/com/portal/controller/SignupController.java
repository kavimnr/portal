package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.impl.UserDao;
import com.portal.model.User;


public class SignupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext application=request.getServletContext();
		Connection con= (Connection)application.getAttribute("connection");
		UserDao connection=UserDao.getConnection(con);
		User user=new User();
		
		String username=(String)request.getParameter("username");
		String password=(String)request.getParameter("password");
		String role=(String)request.getParameter("role");
		
		
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
		
		try {
			connection.addUser(user);
			HttpSession session=request.getSession();
			session.setAttribute("username", username);
			RequestDispatcher rd=request.getRequestDispatcher("success-page.jsp");
			rd.forward(request, response);
		} 
		catch (Exception e) 
		{
			RequestDispatcher rd=request.getRequestDispatcher("signup-error.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}
	}	
}
