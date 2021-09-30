package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.portal.dao.impl.EmployeeDao;
import com.portal.model.Employee;

public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		List<Employee> list = null;
		
		ServletContext context=request.getServletContext();
		Connection con=(Connection)context.getAttribute("connection");
		
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		String count=(String)context.getAttribute("total");
		int total=Integer.parseInt(count);
        
		System.out.println(request.getParameter("page_no"));
		
		int id=0;
		String emp_id=(String)request.getParameter("emp_id");
		if(emp_id!="")
			id=Integer.parseInt(emp_id);
		String name=(String)request.getParameter("name");
		String department=(String)request.getParameter("department");
		String reporting_to=(String)request.getParameter("reporting_to");
		String email=(String)request.getParameter("email");
		String mobile=(String)request.getParameter("mobile");
		String location=(String)request.getParameter("location");
		
		
		if(emp_id!="")
		{
			try {
				list = dao.getEmployeesById(id,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(name!="")
		{
			try {
				list = dao.getEmployeesByName(name,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(department!="")
		{
			try {
				list = dao.getEmployeesByDeparment(department,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(reporting_to!="")
		{
			try {
				list = dao.getEmployeesByReportingManager(reporting_to,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(email!="")
		{
			try {
				list = dao.getEmployeesByEmail(email,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(mobile!="")
		{
			try {
				list = dao.getEmployeesBymobile(mobile,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else if(location!="")
		{
			try {
				list = dao.getEmployeesByLocation(location,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		else
		{
			try {
				list = dao.viewEmployees(1,total);
			} catch (SQLException e1) {e1.printStackTrace();}
		}
		request.setAttribute("list", list);
		HttpSession session=request.getSession();
		session.setAttribute("emp_id", emp_id);
		session.setAttribute("name", name);
		session.setAttribute("department", department);
		session.setAttribute("reporting_to", reporting_to);
		session.setAttribute("email", email);
		session.setAttribute("mobile", mobile);
		session.setAttribute("location", location);				
		RequestDispatcher rd=request.getRequestDispatcher("view-employees.jsp");
		rd.include(request, response);
	}

}
