package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.impl.EmployeeDao;

/**
 * Servlet implementation class DeleteController
 */
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session=request.getSession();
		int emp_id=Integer.parseInt(request.getParameter("emp_id"));
		ServletContext context=request.getServletContext();
		Connection con=(Connection)context.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		try {
			int result=dao.deleteEmployee(emp_id);
			if(result==1)
			{
				session.setAttribute("message", "Your request has been completed.");
				response.sendRedirect("home.jsp");
			}
			else
			{
				session.setAttribute("message", "Invalid request. Employee not found");
				response.sendRedirect("home.jsp");
			}
		} catch (SQLException e) 
		{
			session.setAttribute("message", "An error occuured");
			response.sendRedirect("home.jsp");
			e.printStackTrace();
		}
	}

}
