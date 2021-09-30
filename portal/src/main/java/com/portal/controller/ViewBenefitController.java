package com.portal.controller;

import java.io.IOException;
import java.sql.Connection;
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

/**
 * Servlet implementation class ViewBenefitController
 */
public class ViewBenefitController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		List<String> list=new ArrayList<String>();
		ServletContext context=request.getServletContext();
		String id=request.getParameter("emp_id");
		int emp_id=Integer.parseInt(id);
		Connection con=(Connection)context.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		try {
			list=dao.getEmployeeDetails(emp_id);
		} catch (DataAccessException e) 
		{
			e.printStackTrace();
		} 
		request.setAttribute("list", list);
		RequestDispatcher rd=request.getRequestDispatcher("view-benefits.jsp");
		rd.include(request, response);
	}

}
