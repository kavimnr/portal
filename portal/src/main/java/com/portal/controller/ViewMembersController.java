package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adventnet.persistence.DataAccessException;
import com.portal.dao.impl.EmployeeDao;
import com.portal.model.Employee;

/**
 * Servlet implementation class ViewMembersController
 */
public class ViewMembersController extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Employee emp=null;
		List<Employee> list=new ArrayList<Employee>();
		ServletContext context=request.getServletContext();
		String id=request.getParameter("emp_id");
		int emp_id=Integer.parseInt(id);
		Connection con=(Connection)context.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		try {
			emp=dao.getEmployeeById(emp_id);
		} catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		try {
			list=dao.getEmployeesByReportingManager(emp.getName(),5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("list", list);
		RequestDispatcher rd=request.getRequestDispatcher("view-employees.jsp");
		rd.include(request, response);
	}

}
