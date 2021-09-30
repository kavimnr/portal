package com.portal.controller;

import java.io.IOException;
import java.io.PrintWriter;
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


public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session=request.getSession();
		session.setAttribute("emp_id", request.getParameter("emp_id"));
		
		Employee employee=null;
		ServletContext application=request.getServletContext();
		Connection con= (Connection)application.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		String id=(String)request.getParameter("emp_id");
		int emp_id=Integer.parseInt(id);
		try {
			employee=dao.getEmployeeById(emp_id);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		if(employee.getName()==""||employee.getName()==null)
		{
			session.setAttribute("message", "Invalid request. Employee Not found");
			
			response.sendRedirect("home.jsp");
			
		}
		session.setAttribute("name", employee.getName());
		session.setAttribute("department", employee.getDepartment());
		session.setAttribute("reporting_to", employee.getReporting_to());
		session.setAttribute("email", employee.getEmail());
		session.setAttribute("mobile", employee.getMobile());
		session.setAttribute("location", employee.getLocation());
		session.setAttribute("employee", employee);
		RequestDispatcher rd=request.getRequestDispatcher("update-employee.jsp");
		rd.include(request, response);
	}
}
