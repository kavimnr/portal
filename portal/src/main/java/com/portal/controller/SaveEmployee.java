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
import com.portal.model.Employee;

/**
 * Servlet implementation class SaveEmployee
 */
public class SaveEmployee extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Employee emp=new Employee();
		
		ServletContext application=request.getServletContext();
		Connection con= (Connection)application.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		String name=(String)request.getParameter("name");
		String department=(String)request.getParameter("department");
		String reporting_to=(String)request.getParameter("reporting_to");
		String email=(String)request.getParameter("email");
		String mobile=(String)request.getParameter("mobile");
		String location=(String)request.getParameter("location");
		
		emp.setName(name);
		emp.setDepartment(department);
		emp.setReporting_to(reporting_to);
		emp.setEmail(email);
		emp.setMobile(mobile);
		emp.setLocation(location);
		HttpSession session=request.getSession();
		try {
			dao.save(emp);
			session.setAttribute("message", "Your request has been completed.");
			response.sendRedirect("home.jsp");
		} catch (SQLException e) 
		{
			session.setAttribute("message", "Invalid request.! Employee Already Exists");
			response.sendRedirect("home.jsp");
			System.out.print("Error");
			e.printStackTrace();
		}
	
	}	

}
