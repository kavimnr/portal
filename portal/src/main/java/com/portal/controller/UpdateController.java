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


public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Employee employee=null;
		ServletContext application=request.getServletContext();
		Connection con= (Connection)application.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		String name=(String)request.getParameter("name");
		String department=(String)request.getParameter("department");
		String reporting_to=(String)request.getParameter("reporting_to");
		String email=(String)request.getParameter("email");
		String mobile=(String)request.getParameter("mobile");
		String location=(String)request.getParameter("location");
		
		HttpSession session=request.getSession();
		int emp_id=Integer.parseInt((String) session.getAttribute("emp_id"));
		try {
			employee=dao.getEmployeeById(emp_id);
			employee.setName(name);
			employee.setDepartment(department);
			employee.setReporting_to(reporting_to);
			employee.setEmail(email);
			employee.setMobile(mobile);
			employee.setLocation(location);
			if(dao.updateEmployee(employee)==1)
			{
				session.setAttribute("message", "Your request has been completed.");
				response.sendRedirect("home.jsp");
			}
		} catch (SQLException e) 
		{
			session.setAttribute("message", "Invalid request. Employee Not found");
			response.sendRedirect("home.jsp");
			e.printStackTrace();
		}
		
		
		
	}
}
