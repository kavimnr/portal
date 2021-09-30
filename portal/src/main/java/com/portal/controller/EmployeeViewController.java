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

import com.portal.dao.impl.EmployeeDao;
import com.portal.model.Employee;

public class EmployeeViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext context=request.getServletContext();
		Connection con=(Connection)context.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		String count=(String)context.getAttribute("total");
		int total=Integer.parseInt(count);
        
		String no=(String)request.getParameter("page_no");
		int page_no=Integer.parseInt(no);
		
		List<Employee> list = null;
		try { 
			list = dao.viewEmployees(page_no,total);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}  
		request.setAttribute("list", list);
		RequestDispatcher rd=request.getRequestDispatcher("view-employees.jsp");
		rd.include(request, response);
	}
}
