package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.impl.UserDao;
import com.portal.model.User;


public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		User user=null;
		
		ServletContext application=request.getServletContext();
		Connection con= (Connection)application.getAttribute("connection");
		
		UserDao connection=UserDao.getConnection(con);
		
		String username=(String)request.getParameter("username");
		String password=(String)request.getParameter("password");
		try {
			user=connection.getUser(username);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		if(user!=null&&user.getUsername()!=null&&user.getPassword()!=null)
		{
			
			if(user.getUsername().equals(username)&&user.getPassword().equals(password))
			{
				Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Loggedin successfull");;
				
				HttpSession session=request.getSession();
				session.setAttribute("username", username);
				RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd=request.getRequestDispatcher("login-error.jsp");
				rd.forward(request, response);
			}
		}
		else
		{
			RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		}
	}

}
