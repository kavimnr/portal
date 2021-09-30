package com.portal.handler;

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

/**
 * Servlet implementation class SortHandler
 */
public class SortHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ServletContext context=request.getServletContext();
		Connection con=(Connection)context.getAttribute("connection");
		EmployeeDao dao=EmployeeDao.getInstance(con);
		
		String count=(String)context.getAttribute("total");
		String sort_by=request.getParameter("sort_by");
		String mode=request.getParameter("mode");
		if(sort_by==""&&mode=="")
		{
			mode="asc";
			sort_by="name";
		}
		if(sort_by!="")
		{
			if(mode=="")
				mode="asc";
		}
		if(mode!="")
		{
			if(sort_by=="")
				sort_by="name";
		}
		
		List<Employee> list = null;
		try {
			list = dao.viewEmployeesSorted(sort_by,mode,Integer.parseInt(count));
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}  
		request.setAttribute("list", list);
		RequestDispatcher rd=request.getRequestDispatcher("view-employees.jsp");
		rd.include(request, response);
	}

}
